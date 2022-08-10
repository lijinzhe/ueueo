package com.ueueo.ddd.domain.entities;

import lombok.AccessLevel;
import lombok.Setter;

/**
 * @author Lee
 * @date 2021-08-20 16:48
 */
public abstract class Entity<TKey> implements IEntity<TKey> {

    @Setter(AccessLevel.PROTECTED)
    protected TKey id;

    @Override
    public TKey getId() {
        return id;
    }

    protected Entity() {
        EntityHelper.trySetTenantId(this);
    }

    protected Entity(TKey id) {
        this();
        this.id = id;
    }
}
