package com.ueueo.ddd.domain.entities.events;

/**
 * This type of event can be used to notify just after update of an Entity.
 *
 * <typeparam name="TEntity">Entity type</typeparam>
 */
public class EntityUpdatedEventData<TEntity> extends EntityChangedEventData<TEntity> {
    /**
     * Constructor.
     *
     * <param name="entity">The entity which is updated</param>
     */
    public EntityUpdatedEventData(TEntity entity) {
        super(entity);
    }
}
