package com.ueueo.ddd.domain.entities.events;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 14:20
 */
public class EntityChangedEventData<TEntity> extends EntityEventData<TEntity> {
    public EntityChangedEventData(TEntity tEntity) {
        super(tEntity);
    }
}
