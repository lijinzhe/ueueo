package com.ueueo.ddd.domain.entities.events;

import lombok.Getter;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-25 14:44
 */
@Getter
public class DomainEventEntry {
    private final Object sourceEntity;
    private final Object eventData;
    private final long eventOrder;

    public DomainEventEntry(Object sourceEntity, Object eventData, long eventOrder) {
        this.sourceEntity = sourceEntity;
        this.eventData = eventData;
        this.eventOrder = eventOrder;
    }
}
