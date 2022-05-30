package com.ueueo.eventbus;

import com.ueueo.eventbus.distributed.IDistributedEventBus;
import com.ueueo.eventbus.local.ILocalEventBus;

public class UnitOfWorkEventPublisher implements IUnitOfWorkEventPublisher
{
    private ILocalEventBus _localEventBus;
    private IDistributedEventBus _distributedEventBus;

    public UnitOfWorkEventPublisher(
        ILocalEventBus localEventBus,
        IDistributedEventBus distributedEventBus)
    {
        _localEventBus = localEventBus;
        _distributedEventBus = distributedEventBus;
    }

    public void PublishLocalEventsAsync(IEnumerable<UnitOfWorkEventRecord> localEvents)
    {
        for (var localEvent in localEvents)
        {
            _localEventBus.publish(
                localEvent.EventType,
                localEvent.EventData,
                onUnitOfWorkComplete: false
            );
        }
    }

    public void PublishDistributedEventsAsync(IEnumerable<UnitOfWorkEventRecord> distributedEvents)
    {
        for (var distributedEvent in distributedEvents)
        {
            _distributedEventBus.publish(
                distributedEvent.EventType,
                distributedEvent.EventData,
                onUnitOfWorkComplete: false,
                useOutbox: distributedEvent.UseOutbox
            );
        }
    }
}
