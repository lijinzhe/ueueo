package com.ueueo.ddd.domain.entities;

import com.ueueo.ID;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 16:48
 */
public abstract class Entity implements IEntity {
    protected ID id;

    @Override
    public ID getId() {
        return id;
    }
}
