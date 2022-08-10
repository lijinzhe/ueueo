package com.ueueo.ddd.domain.entities.events;

/**
 * This type of event can be used to notify just after deletion of an Entity.
 *
 * <typeparam name="TEntity">Entity type</typeparam>
 */
public class EntityDeletedEventData<TEntity> extends EntityChangedEventData<TEntity> {
    /**
     * Constructor.
     *
     * <param name="entity">The entity which is deleted</param>
     */
    public EntityDeletedEventData(TEntity entity) {
        super(entity);
    }
}
