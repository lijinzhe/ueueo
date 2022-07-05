package com.ueueo.eventbus.local;

import com.ueueo.ID;
import lombok.Getter;

@Getter
public class LocalEventMessage {
    private ID messageId;

    private Object eventData;

    private Class<?> eventType;

    private Class<?> genericArgumentType;

    public LocalEventMessage(ID messageId, Object eventData, Class<?> eventType,Class<?> genericArgumentType) {
        this.messageId = messageId;
        this.eventData = eventData;
        this.eventType = eventType;
        this.genericArgumentType = genericArgumentType;
    }
}
