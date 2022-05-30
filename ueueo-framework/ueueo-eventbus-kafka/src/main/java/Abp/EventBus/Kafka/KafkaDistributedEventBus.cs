using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Confluent.Kafka;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;
using Volo.Abp.EventBus.Distributed;
using Volo.Abp.Guids;
using Volo.Abp.Kafka;
using Volo.Abp.MultiTenancy;
using Volo.Abp.Threading;
using Volo.Abp.Timing;
using Volo.Abp.Uow;

namespace Volo.Abp.EventBus.Kafka;

[Dependency(ReplaceServices = true)]
[ExposeServices(typeof(IDistributedEventBus), typeof(KafkaDistributedEventBus))]
public class KafkaDistributedEventBus : DistributedEventBusBase, ISingletonDependency
{
    protected AbpKafkaEventBusOptions AbpKafkaEventBusOptions;//  { get; }
    protected IKafkaMessageConsumerFactory MessageConsumerFactory;//  { get; }
    protected IKafkaSerializer Serializer;//  { get; }
    protected IProducerPool ProducerPool;//  { get; }
    protected ConcurrentDictionary<Type, List<IEventHandlerFactory>> HandlerFactories;//  { get; }
    protected ConcurrentDictionary<String, Type> EventTypes;//  { get; }
    protected IKafkaMessageConsumer Consumer ;// { get; private set; }

    public KafkaDistributedEventBus(
        IServiceScopeFactory serviceScopeFactory,
        ICurrentTenant currentTenant,
        IUnitOfWorkManager unitOfWorkManager,
        IOptions<AbpKafkaEventBusOptions> abpKafkaEventBusOptions,
        IKafkaMessageConsumerFactory messageConsumerFactory,
        IOptions<AbpDistributedEventBusOptions> abpDistributedEventBusOptions,
        IKafkaSerializer serializer,
        IProducerPool producerPool,
        IGuidGenerator guidGenerator,
        IClock clock,
        IEventHandlerInvoker eventHandlerInvoker)
        : base(
            serviceScopeFactory,
            currentTenant,
            unitOfWorkManager,
            abpDistributedEventBusOptions,
            guidGenerator,
            clock,
            eventHandlerInvoker)
    {
        AbpKafkaEventBusOptions = abpKafkaEventBusOptions.Value;
        MessageConsumerFactory = messageConsumerFactory;
        Serializer = serializer;
        ProducerPool = producerPool;

        HandlerFactories = new ConcurrentDictionary<Type, List<IEventHandlerFactory>>();
        EventTypes = new ConcurrentDictionary<String, Type>();
    }

    public void Initialize()
    {
        Consumer = MessageConsumerFactory.Create(
            AbpKafkaEventBusOptions.TopicName,
            AbpKafkaEventBusOptions.GroupId,
            AbpKafkaEventBusOptions.ConnectionName);
        Consumer.OnMessageReceived(ProcessEventAsync);

        SubscribeHandlers(AbpDistributedEventBusOptions.Handlers);
    }

    private void ProcessEventAsync(Message<String, byte[]> message)
    {
        var eventName = message.Key;
        var eventType = EventTypes.GetOrDefault(eventName);
        if (eventType == null)
        {
            return;
        }

        var messageId = message.GetMessageId();

        if (AddToInboxAsync(messageId, eventName, eventType, message.Value))
        {
            return;
        }

        var eventData = Serializer.Deserialize(message.Value, eventType);

        TriggerHandlersAsync(eventType, eventData);
    }

    @Override
    public IDisposable Subscribe(Type eventType, IEventHandlerFactory factory)
    {
        var handlerFactories = GetOrCreateHandlerFactories(eventType);

        if (factory.IsInFactories(handlerFactories))
        {
            return NullDisposable.Instance;
        }

        handlerFactories.Add(factory);

        return new EventHandlerFactoryUnregistrar(this, eventType, factory);
    }


    @Override
    public void Unsubscribe<TEvent>(Func<TEvent, Task> action)
    {
        Objects.requireNonNull(action, nameof(action));

        GetOrCreateHandlerFactories(typeof(TEvent))
            .Locking(factories =>
            {
                factories.RemoveAll(
                    factory =>
                    {
                        var singleInstanceFactory = factory as SingleInstanceHandlerFactory;
                        if (singleInstanceFactory == null)
                        {
                            return false;
                        }

                        var actionHandler = singleInstanceFactory.HandlerInstance as ActionEventHandler<TEvent>;
                        if (actionHandler == null)
                        {
                            return false;
                        }

                        return actionHandler.Action == action;
                    });
            });
    }


    @Override
    public void Unsubscribe(Type eventType, IEventHandler handler)
    {
        GetOrCreateHandlerFactories(eventType)
            .Locking(factories =>
            {
                factories.RemoveAll(
                    factory =>
                        factory is SingleInstanceHandlerFactory handlerFactory &&
                        handlerFactory.HandlerInstance == handler
                );
            });
    }


    @Override
    public void Unsubscribe(Type eventType, IEventHandlerFactory factory)
    {
        GetOrCreateHandlerFactories(eventType).Locking(factories => factories.Remove(factory));
    }


    @Override
    public void UnsubscribeAll(Type eventType)
    {
        GetOrCreateHandlerFactories(eventType).Locking(factories => factories.Clear());
    }

    protected  override void PublishToEventBusAsync(Type eventType, Object eventData)
    {
        PublishAsync(
            eventType,
            eventData,
            new Headers
            {
                    { "messageId", System.Text.Encoding.UTF8.GetBytes(Guid.NewGuid().ToString("N")) }
            },
            null
        );
    }

    protected override void AddToUnitOfWork(IUnitOfWork unitOfWork, UnitOfWorkEventRecord eventRecord)
    {
        unitOfWork.AddOrReplaceDistributedEvent(eventRecord);
    }

    @Override
    public void PublishFromOutboxAsync(
        OutgoingEventInfo outgoingEvent,
        OutboxConfig outboxConfig)
    {
        return PublishAsync(
            AbpKafkaEventBusOptions.TopicName,
            outgoingEvent.EventName,
            outgoingEvent.EventData,
            new Headers
            {
                    { "messageId", System.Text.Encoding.UTF8.GetBytes(outgoingEvent.Id.ToString("N")) }
            },
            null
        );
    }

    @Override
    public void PublishManyFromOutboxAsync(IEnumerable<OutgoingEventInfo> outgoingEvents, OutboxConfig outboxConfig)
    {
        var producer = ProducerPool.Get();
        var outgoingEventArray = outgoingEvents.ToArray();
        producer.BeginTransaction();
        try
        {
            for (var outgoingEvent in outgoingEventArray)
            {
                var messageId = outgoingEvent.Id.ToString("N");
                var headers = new Headers
                {
                    { "messageId", System.Text.Encoding.UTF8.GetBytes(messageId)}
                };

                 producer.Produce(
                    AbpKafkaEventBusOptions.TopicName,
                    new Message<String, byte[]>
                    {
                        Key = outgoingEvent.EventName,
                        Value = outgoingEvent.EventData,
                        Headers = headers
                    });
            }

            producer.CommitTransaction();
        }
        catch (Exception e)
        {
            producer.AbortTransaction();
            throw;
        }

        return Task.CompletedTask;
    }

    @Override
    public void ProcessFromInboxAsync(
        IncomingEventInfo incomingEvent,
        InboxConfig inboxConfig)
    {
        var eventType = EventTypes.GetOrDefault(incomingEvent.EventName);
        if (eventType == null)
        {
            return;
        }

        var eventData = Serializer.Deserialize(incomingEvent.EventData, eventType);
        var exceptions = new List<Exception>();
        TriggerHandlersAsync(eventType, eventData, exceptions, inboxConfig);
        if (exceptions.Any())
        {
            ThrowOriginalExceptions(eventType, exceptions);
        }
    }

    protected override byte[] Serialize(Object eventData)
    {
        return Serializer.Serialize(eventData);
    }

    public   void PublishAsync(Type eventType, Object eventData, Headers headers, Dictionary<String, Object> headersArguments)
    {
        PublishAsync(
            AbpKafkaEventBusOptions.TopicName,
            eventType,
            eventData,
            headers,
            headersArguments
        );
    }

    private void PublishAsync(String topicName, Type eventType, Object eventData, Headers headers, Dictionary<String, Object> headersArguments)
    {
        var eventName = EventNameAttribute.GetNameOrDefault(eventType);
        var body = Serializer.Serialize(eventData);

        return PublishAsync(topicName, eventName, body, headers, headersArguments);
    }

    private Task<DeliveryResult<String, byte[]>> PublishAsync(
        String topicName,
        String eventName,
        byte[] body,
        Headers headers,
        Dictionary<String, Object> headersArguments)
    {
        var producer = ProducerPool.Get(AbpKafkaEventBusOptions.ConnectionName);

        return PublishAsync(producer, topicName, eventName, body, headers, headersArguments);
    }

    private Task<DeliveryResult<String, byte[]>> PublishAsync(
        IProducer<String, byte[]> producer,
        String topicName,
        String eventName,
        byte[] body,
        Headers headers,
        Dictionary<String, Object> headersArguments)
    {
        SetEventMessageHeaders(headers, headersArguments);

        return producer.ProduceAsync(
            topicName,
            new Message<String, byte[]>
            {
                Key = eventName,
                Value = body,
                Headers = headers
            });
    }

    private void SetEventMessageHeaders(Headers headers, Dictionary<String, Object> headersArguments)
    {
        if (headersArguments == null)
        {
            return;
        }

        for (var header in headersArguments)
        {
            headers.Remove(header.Key);
            headers.Add(header.Key, Serializer.Serialize(header.Value));
        }
    }

    private List<IEventHandlerFactory> GetOrCreateHandlerFactories(Type eventType)
    {
        return HandlerFactories.GetOrAdd(
            eventType,
            type =>
            {
                var eventName = EventNameAttribute.GetNameOrDefault(type);
                EventTypes[eventName] = type;
                return new List<IEventHandlerFactory>();
            }
        );
    }

    protected override IEnumerable<EventTypeWithEventHandlerFactories> GetHandlerFactories(Type eventType)
    {
        var handlerFactoryList = new List<EventTypeWithEventHandlerFactories>();

        for (var handlerFactory in HandlerFactories.Where(hf => ShouldTriggerEventForHandler(eventType, hf.Key))
        )
        {
            handlerFactoryList.Add(
                new EventTypeWithEventHandlerFactories(handlerFactory.Key, handlerFactory.Value));
        }

        return handlerFactoryList.ToArray();
    }

    private static boolean ShouldTriggerEventForHandler(Type targetEventType, Type handlerEventType)
    {
        //Should trigger same type
        if (handlerEventType == targetEventType)
        {
            return true;
        }

        //Should trigger for inherited types
        if (handlerEventType.IsAssignableFrom(targetEventType))
        {
            return true;
        }

        return false;
    }
}
