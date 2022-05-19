package com.ueueo.ddd.domain.entities;

import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-19 11:15
 */
@Getter
public class DomainEventRecord {
    private final Object eventData;

    private final long eventOrder;

    public DomainEventRecord(Object eventData, long eventOrder) {
        this.eventData = eventData;
        this.eventOrder = eventOrder;
    }
}
