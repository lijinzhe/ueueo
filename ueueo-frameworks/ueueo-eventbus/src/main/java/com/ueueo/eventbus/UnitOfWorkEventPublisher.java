package com.ueueo.eventbus;

import com.ueueo.eventbus.distributed.IDistributedEventBus;
import com.ueueo.eventbus.local.ILocalEventBus;
import com.ueueo.uow.IUnitOfWorkEventPublisher;
import com.ueueo.uow.UnitOfWorkEventRecord;

import java.util.Collection;

public class UnitOfWorkEventPublisher implements IUnitOfWorkEventPublisher {
    private ILocalEventBus localEventBus;
    private IDistributedEventBus distributedEventBus;

    public UnitOfWorkEventPublisher(
            ILocalEventBus localEventBus,
            IDistributedEventBus distributedEventBus) {
        this.localEventBus = localEventBus;
        this.distributedEventBus = distributedEventBus;
    }

    @Override
    public void publishLocalEvents(Collection<UnitOfWorkEventRecord> localEvents) {
        for (UnitOfWorkEventRecord localEvent : localEvents) {
            localEventBus.publish(
                    localEvent.getEventType(),
                    localEvent.getGenericArgumentType(),
                    localEvent.getEventData(),
                    false
            );
        }
    }

    @Override
    public void publishDistributedEvents(Collection<UnitOfWorkEventRecord> distributedEvents) {
        for (UnitOfWorkEventRecord distributedEvent : distributedEvents) {
            distributedEventBus.publish(
                    distributedEvent.getEventType(),
                    distributedEvent.getGenericArgumentType(),
                    distributedEvent.getEventData(),
                    false
            );
        }
    }

}
