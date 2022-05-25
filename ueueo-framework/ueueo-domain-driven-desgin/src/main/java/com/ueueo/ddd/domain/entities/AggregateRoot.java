package com.ueueo.ddd.domain.entities;

import com.ueueo.ID;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 11:28
 */
public abstract class AggregateRoot extends BasicAggregateRoot{
    public AggregateRoot() {
        super();
    }

    public AggregateRoot(ID id) {
        super(id);
    }
}
