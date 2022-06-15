package com.ueueo.eventbus.local;

import com.ueueo.ID;
import lombok.Getter;

@Getter
public class LocalEventMessage {
    private ID messageId;

    private Object eventData;

    private Class<?> eventType;

    public LocalEventMessage(ID messageId, Object eventData, Class<?> eventType) {
        this.messageId = messageId;
        this.eventData = eventData;
        this.eventType = eventType;
    }
}
