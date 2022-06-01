package com.ueueo.uow;

import java.util.Collection;

public interface IUnitOfWorkEventPublisher {
    void publishLocalEvents(Collection<UnitOfWorkEventRecord> localEvents);

    void publishDistributedEvents(Collection<UnitOfWorkEventRecord> distributedEvents);
}
