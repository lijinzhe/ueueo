package com.ueueo.ddd.domain.entities;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 16:48
 */
public abstract class Entity<TKey> implements IEntity<TKey> {
    protected TKey id;

    @Override
    public TKey id() {
        return id;
    }
}
