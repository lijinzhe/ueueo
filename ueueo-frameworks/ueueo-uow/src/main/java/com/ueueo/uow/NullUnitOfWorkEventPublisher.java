package com.ueueo.uow;

import java.util.Collection;

public class NullUnitOfWorkEventPublisher implements IUnitOfWorkEventPublisher {
    @Override
    public void publishLocalEvents(Collection<UnitOfWorkEventRecord> localEvents) {

    }

    @Override
    public void publishDistributedEvents(Collection<UnitOfWorkEventRecord> distributedEvents) {

    }
}
