package com.ueueo.ddd.domain.entities;

import com.ueueo.ID;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * @author Lee
 * @date 2021-08-20 16:48
 */
public abstract class Entity implements IEntity {

    @Setter(AccessLevel.PROTECTED)
    protected ID id;

    @Override
    public ID getId() {
        return id;
    }

    protected Entity() {
        EntityHelper.trySetTenantId(this);
    }

    protected Entity(ID id) {
        this();
        this.id = id;
    }
}
