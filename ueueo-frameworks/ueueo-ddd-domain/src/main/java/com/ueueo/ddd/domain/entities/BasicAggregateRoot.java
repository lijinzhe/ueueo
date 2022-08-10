package com.ueueo.ddd.domain.entities;

import com.ueueo.uow.EventOrderGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-19 11:18
 */
@Getter
public abstract class BasicAggregateRoot<TKey> extends Entity<TKey> implements IAggregateRoot<TKey>, IGeneratesDomainEvents {

    private final List<DomainEventRecord> distributedEvents = new ArrayList<>();
    private final List<DomainEventRecord> localEvents = new ArrayList<>();

    public BasicAggregateRoot() {
        super();
    }

    public BasicAggregateRoot(TKey id) {
        super(id);
    }

    @Override
    public void clearLocalEvents() {
        localEvents.clear();
    }

    @Override
    public void clearDistributedEvents() {
        distributedEvents.clear();
    }

    protected void addLocalEvent(Object eventData) {
        localEvents.add(new DomainEventRecord(eventData, EventOrderGenerator.getNext()));
    }

    protected void addDistributedEvent(Object eventData) {
        distributedEvents.add(new DomainEventRecord(eventData, EventOrderGenerator.getNext()));
    }
}
