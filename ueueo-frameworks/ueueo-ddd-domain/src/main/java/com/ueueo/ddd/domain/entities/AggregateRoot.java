package com.ueueo.ddd.domain.entities;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 11:28
 */
public abstract class AggregateRoot<TKey> extends BasicAggregateRoot<TKey> {
    public AggregateRoot() {
        super();
    }

    public AggregateRoot(TKey id) {
        super(id);
    }
}
