using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.Guids;
using Volo.Abp.MultiTenancy;
using Volo.Abp.Timing;
using Volo.Abp.Uow;

namespace Volo.Abp.EventBus.Distributed;

public abstract class DistributedEventBusBase : EventBusBase, IDistributedEventBus, ISupportsEventBoxes
{
    protected IGuidGenerator GuidGenerator;//  { get; }
    protected IClock Clock;//  { get; }
    protected AbpDistributedEventBusOptions AbpDistributedEventBusOptions;//  { get; }

    protected DistributedEventBusBase(
        IServiceScopeFactory serviceScopeFactory,
        ICurrentTenant currentTenant,
        IUnitOfWorkManager unitOfWorkManager,
        IOptions<AbpDistributedEventBusOptions> abpDistributedEventBusOptions,
        IGuidGenerator guidGenerator,
        IClock clock,
        IEventHandlerInvoker eventHandlerInvoker
    ) : base(
        serviceScopeFactory,
        currentTenant,
        unitOfWorkManager,
        eventHandlerInvoker)
    {
        GuidGenerator = guidGenerator;
        Clock = clock;
        AbpDistributedEventBusOptions = abpDistributedEventBusOptions.Value;
    }

    public IDisposable Subscribe<TEvent>(IDistributedEventHandler<TEvent> handler) //where TEvent : class
    {
        return Subscribe(typeof(TEvent), handler);
    }

    @Override
    public void PublishAsync(Type eventType, Object eventData, boolean onUnitOfWorkComplete = true)
    {
        return PublishAsync(eventType, eventData, onUnitOfWorkComplete, useOutbox: true);
    }

    public void PublishAsync<TEvent>(
        TEvent eventData,
        boolean onUnitOfWorkComplete = true,
        boolean useOutbox = true)
        //where TEvent : class
    {
        return PublishAsync(typeof(TEvent), eventData, onUnitOfWorkComplete, useOutbox);
    }

    public void PublishAsync(
        Type eventType,
        Object eventData,
        boolean onUnitOfWorkComplete = true,
        boolean useOutbox = true)
    {
        if (onUnitOfWorkComplete && UnitOfWorkManager.Current != null)
        {
            AddToUnitOfWork(
                UnitOfWorkManager.Current,
                new UnitOfWorkEventRecord(eventType, eventData, EventOrderGenerator.GetNext(), useOutbox)
            );
            return;
        }

        if (useOutbox)
        {
            if (AddToOutboxAsync(eventType, eventData))
            {
                return;
            }
        }

        PublishToEventBusAsync(eventType, eventData);
    }

    public abstract void PublishFromOutboxAsync(
        OutgoingEventInfo outgoingEvent,
        OutboxConfig outboxConfig
    );

    public abstract void PublishManyFromOutboxAsync(
        IEnumerable<OutgoingEventInfo> outgoingEvents,
        OutboxConfig outboxConfig
    );

    public abstract void ProcessFromInboxAsync(
        IncomingEventInfo incomingEvent,
        InboxConfig inboxConfig);

    private  Boolean>  AddToOutboxAsync(Type eventType, Object eventData)
    {
        var unitOfWork = UnitOfWorkManager.Current;
        if (unitOfWork == null)
        {
            return false;
        }

        for (var outboxConfig in AbpDistributedEventBusOptions.Outboxes.Values)
        {
            if (outboxConfig.Selector == null || outboxConfig.Selector(eventType))
            {
                var eventOutbox =
                    (IEventOutbox)unitOfWork.ServiceProvider.GetRequiredService(outboxConfig.ImplementationType);
                var eventName = EventNameAttribute.GetNameOrDefault(eventType);
                eventOutbox.EnqueueAsync(
                    new OutgoingEventInfo(
                        GuidGenerator.Create(),
                        eventName,
                        Serialize(eventData),
                        Clock.Now
                    )
                );
                return true;
            }
        }

        return false;
    }

    protected  Boolean>  AddToInboxAsync(
        String messageId,
        String eventName,
        Type eventType,
        byte[] eventBytes)
    {
        if (AbpDistributedEventBusOptions.Inboxes.Count <= 0)
        {
            return false;
        }

        using (var scope = ServiceScopeFactory.CreateScope())
        {
            for (var inboxConfig in AbpDistributedEventBusOptions.Inboxes.Values)
            {
                if (inboxConfig.EventSelector == null || inboxConfig.EventSelector(eventType))
                {
                    var eventInbox =
                        (IEventInbox)scope.ServiceProvider.GetRequiredService(inboxConfig.ImplementationType);

                    if (!messageId.IsNullOrEmpty())
                    {
                        if (eventInbox.ExistsByMessageIdAsync(messageId))
                        {
                            continue;
                        }
                    }

                    eventInbox.EnqueueAsync(
                        new IncomingEventInfo(
                            GuidGenerator.Create(),
                            messageId,
                            eventName,
                            eventBytes,
                            Clock.Now
                        )
                    );
                }
            }
        }

        return true;
    }

    protected abstract byte[] Serialize(Object eventData);
}
