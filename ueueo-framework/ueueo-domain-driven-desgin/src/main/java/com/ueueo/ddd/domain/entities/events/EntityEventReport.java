package com.ueueo.ddd.domain.entities.events;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EntityEventReport {
    private List<DomainEventEntry> domainEvents;

    private List<DomainEventEntry> distributedEvents;

    public EntityEventReport() {
        domainEvents = new ArrayList<>();
        distributedEvents = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("[EntityEventReport] DomainEvents: %s, DistributedEvents: %s", domainEvents.size(), distributedEvents.size());
    }
}
