package com.ueueo.ddd.domain.entities.events;

/**
 * Used to trigger entity change events.
 */
public interface IEntityChangeEventHelper {
    void publishEntityCreatedEvent(Object entity);

    void publishEntityUpdatedEvent(Object entity);

    void publishEntityDeletedEvent(Object entity);
}
