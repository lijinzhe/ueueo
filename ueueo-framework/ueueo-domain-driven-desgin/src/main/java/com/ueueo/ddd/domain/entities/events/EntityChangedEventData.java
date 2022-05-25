package com.ueueo.ddd.domain.entities.events;

/**
 * Used to pass data for an event when an entity (<see cref="IEntity"/>) is changed (created, updated or deleted).
 * See <see cref="EntityCreatedEventData{TEntity}"/>, <see cref="EntityDeletedEventData{TEntity}"/> and <see cref="EntityUpdatedEventData{TEntity}"/> classes.
 *
 * @author Lee
 * @date 2022-05-20 14:20
 */
public class EntityChangedEventData<TEntity> extends EntityEventData<TEntity> {
    /**
     * Constructor
     *
     * @param entity Changed entity in this event
     */
    public EntityChangedEventData(TEntity entity) {
        super(entity);
    }
}
