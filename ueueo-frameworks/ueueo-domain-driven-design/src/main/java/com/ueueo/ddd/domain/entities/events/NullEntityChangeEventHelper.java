package com.ueueo.ddd.domain.entities.events;

import lombok.Getter;

/**
 * Null-object implementation of <see cref="IEntityChangeEventHelper"/>.
 */
@Getter
public class NullEntityChangeEventHelper implements IEntityChangeEventHelper {
    /**
     * Gets single instance of <see cref="NullEntityChangeEventHelper"/> class.
     */
    private static NullEntityChangeEventHelper Instance = new NullEntityChangeEventHelper();

    private NullEntityChangeEventHelper() {
    }

    @Override
    public void publishEntityCreatedEvent(Object entity) {

    }

    @Override
    public void publishEntityUpdatedEvent(Object entity) {

    }

    @Override
    public void publishEntityDeletedEvent(Object entity) {

    }
}
