using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using Volo.Abp.DependencyInjection;
using Volo.Abp.EventBus.Distributed;
using Volo.Abp.Guids;
using Volo.Abp.MultiTenancy;
using Volo.Abp.RabbitMQ;
using Volo.Abp.Threading;
using Volo.Abp.Timing;
using Volo.Abp.Uow;

namespace Volo.Abp.EventBus.RabbitMq;

/* TODO: How to handle unsubscribe to unbind on RabbitMq (may not be possible for)
 */
[Dependency(ReplaceServices = true)]
[ExposeServices(typeof(IDistributedEventBus), typeof(RabbitMqDistributedEventBus))]
public class RabbitMqDistributedEventBus : DistributedEventBusBase, ISingletonDependency
{
    protected AbpRabbitMqEventBusOptions AbpRabbitMqEventBusOptions;//  { get; }
    protected IConnectionPool ConnectionPool;//  { get; }
    protected IRabbitMqSerializer Serializer;//  { get; }

    //TODO: Accessing to the List<IEventHandlerFactory> may not be thread-safe!
    protected ConcurrentDictionary<Type, List<IEventHandlerFactory>> HandlerFactories;//  { get; }
    protected ConcurrentDictionary<String, Type> EventTypes;//  { get; }
    protected IRabbitMqMessageConsumerFactory MessageConsumerFactory;//  { get; }
    protected IRabbitMqMessageConsumer Consumer ;// { get; private set; }

    private boolean _exchangeCreated;

    public RabbitMqDistributedEventBus(
        IOptions<AbpRabbitMqEventBusOptions> options,
        IConnectionPool connectionPool,
        IRabbitMqSerializer serializer,
        IServiceScopeFactory serviceScopeFactory,
        IOptions<AbpDistributedEventBusOptions> distributedEventBusOptions,
        IRabbitMqMessageConsumerFactory messageConsumerFactory,
        ICurrentTenant currentTenant,
        IUnitOfWorkManager unitOfWorkManager,
        IGuidGenerator guidGenerator,
        IClock clock,
        IEventHandlerInvoker eventHandlerInvoker)
        : base(
            serviceScopeFactory,
            currentTenant,
            unitOfWorkManager,
            distributedEventBusOptions,
            guidGenerator,
            clock,
            eventHandlerInvoker)
    {
        ConnectionPool = connectionPool;
        Serializer = serializer;
        MessageConsumerFactory = messageConsumerFactory;
        AbpRabbitMqEventBusOptions = options.Value;

        HandlerFactories = new ConcurrentDictionary<Type, List<IEventHandlerFactory>>();
        EventTypes = new ConcurrentDictionary<String, Type>();
    }

    public void Initialize()
    {
        Consumer = MessageConsumerFactory.Create(
            new ExchangeDeclareConfiguration(
                AbpRabbitMqEventBusOptions.ExchangeName,
                type: AbpRabbitMqEventBusOptions.GetExchangeTypeOrDefault(),
                durable: true
            ),
            new QueueDeclareConfiguration(
                AbpRabbitMqEventBusOptions.ClientName,
                durable: true,
                exclusive: false,
                autoDelete: false
            ),
            AbpRabbitMqEventBusOptions.ConnectionName
        );

        Consumer.OnMessageReceived(ProcessEventAsync);

        SubscribeHandlers(AbpDistributedEventBusOptions.Handlers);
    }

    private void ProcessEventAsync(IModel channel, BasicDeliverEventArgs ea)
    {
        var eventName = ea.RoutingKey;
        var eventType = EventTypes.GetOrDefault(eventName);
        if (eventType == null)
        {
            return;
        }

        var eventBytes = ea.Body.ToArray();

        if (AddToInboxAsync(ea.BasicProperties.MessageId, eventName, eventType, eventBytes))
        {
            return;
        }

        var eventData = Serializer.Deserialize(eventBytes, eventType);

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

        if (handlerFactories.Count == 1) //TODO: Multi-threading!
        {
            Consumer.BindAsync(EventNameAttribute.GetNameOrDefault(eventType));
        }

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
                        factory is SingleInstanceHandlerFactory &&
                        (factory as SingleInstanceHandlerFactory).HandlerInstance == handler
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
        PublishAsync(eventType, eventData, null);
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
        return PublishAsync(outgoingEvent.EventName, outgoingEvent.EventData, null, eventId: outgoingEvent.Id);
    }

    @Override
    public void PublishManyFromOutboxAsync(
        IEnumerable<OutgoingEventInfo> outgoingEvents,
        OutboxConfig outboxConfig)
    {
        using (var channel = ConnectionPool.Get(AbpRabbitMqEventBusOptions.ConnectionName).CreateModel())
        {
            var outgoingEventArray = outgoingEvents.ToArray();
            channel.ConfirmSelect();

            for (var outgoingEvent in outgoingEventArray)
            {
                PublishAsync(
                    channel,
                    outgoingEvent.EventName,
                    outgoingEvent.EventData,
                    properties: null,
                    eventId: outgoingEvent.Id);
            }

            channel.WaitForConfirmsOrDie();
        }
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

    public void PublishAsync(
        Type eventType,
        Object eventData,
        IBasicProperties properties,
        Dictionary<String, Object> headersArguments = null)
    {
        var eventName = EventNameAttribute.GetNameOrDefault(eventType);
        var body = Serializer.Serialize(eventData);

        return PublishAsync(eventName, body, properties, headersArguments);
    }

    protected void PublishAsync(
        String eventName,
        byte[] body,
        IBasicProperties properties,
        Dictionary<String, Object> headersArguments = null,
        ID eventId = null)
    {
        using (var channel = ConnectionPool.Get(AbpRabbitMqEventBusOptions.ConnectionName).CreateModel())
        {
            return PublishAsync(channel, eventName, body, properties, headersArguments, eventId);
        }
    }

    protected void PublishAsync(
        IModel channel,
        String eventName,
        byte[] body,
        IBasicProperties properties,
        Dictionary<String, Object> headersArguments = null,
        ID eventId = null)
    {
        EnsureExchangeExists(channel);

        if (properties == null)
        {
            properties = channel.CreateBasicProperties();
            properties.DeliveryMode = RabbitMqConsts.DeliveryModes.Persistent;
        }

        if (properties.MessageId.IsNullOrEmpty())
        {
            properties.MessageId = (eventId ?? GuidGenerator.Create()).ToString("N");
        }

        SetEventMessageHeaders(properties, headersArguments);

        channel.BasicPublish(
            exchange: AbpRabbitMqEventBusOptions.ExchangeName,
            routingKey: eventName,
            mandatory: true,
            basicProperties: properties,
            body: body
        );

        return Task.CompletedTask;
    }

    private void EnsureExchangeExists(IModel channel)
    {
        if (_exchangeCreated)
        {
            return;
        }

        try
        {
            channel.ExchangeDeclarePassive(AbpRabbitMqEventBusOptions.ExchangeName);
        }
        catch (Exception)
        {
            channel.ExchangeDeclare(
                AbpRabbitMqEventBusOptions.ExchangeName,
                AbpRabbitMqEventBusOptions.GetExchangeTypeOrDefault(),
                durable: true
            );
        }
        _exchangeCreated = true;
    }

    private void SetEventMessageHeaders(IBasicProperties properties, Dictionary<String, Object> headersArguments)
    {
        if (headersArguments == null)
        {
            return;
        }

        properties.Headers ??= new Dictionary<String, Object>();

        for (var header in headersArguments)
        {
            properties.Headers[header.Key] = header.Value;
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

        for (var handlerFactory in
                 HandlerFactories.Where(hf => ShouldTriggerEventForHandler(eventType, hf.Key)))
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

        //TODO: Support inheritance? But it does not support on subscription to RabbitMq!
        //Should trigger for inherited types
        if (handlerEventType.IsAssignableFrom(targetEventType))
        {
            return true;
        }

        return false;
    }
}
