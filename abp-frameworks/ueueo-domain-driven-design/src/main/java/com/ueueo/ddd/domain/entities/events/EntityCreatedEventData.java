package com.ueueo.ddd.domain.entities.events;

/**
 * This type of event can be used to notify just after creation of an Entity.
 *
 * <typeparam name="TEntity">Entity type</typeparam>
 */
public class EntityCreatedEventData<TEntity> extends EntityChangedEventData<TEntity> {
    /**
     * Constructor.
     *
     * <param name="entity">The entity which is created</param>
     */
    public EntityCreatedEventData(TEntity entity) {
        super(entity);
    }
}
