package com.ueueo.uow;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UnitOfWorkEventRecord {
    private Object eventData;

    private Class<?> eventType;
    private Class<?> genericArgumentType;

    private long eventOrder;

    private boolean useOutbox;

    /**
     * Extra properties can be used if needed.
     */
    private Map<String, Object> Properties = new HashMap<>();

    public UnitOfWorkEventRecord(
            Class<?> eventType,
            Class<?> genericArgumentType,
            Object eventData,
            long eventOrder,
            Boolean useOutbox)
    {
        this.eventType = eventType;
        this.genericArgumentType = genericArgumentType;
        this.eventData = eventData;
        this.eventOrder = eventOrder;
        this.useOutbox = useOutbox != null ? useOutbox : true;
    }
}
