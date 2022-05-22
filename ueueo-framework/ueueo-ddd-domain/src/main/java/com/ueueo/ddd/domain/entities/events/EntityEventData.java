package com.ueueo.ddd.domain.entities.events;

import com.ueueo.ID;
import com.ueueo.eventbus.IEventDataMayHaveTenantId;
import com.ueueo.eventbus.IEventDataWithInheritableGenericArgument;
import com.ueueo.multitenancy.IMultiTenant;
import lombok.Getter;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 14:20
 */
public class EntityEventData<TEntity> implements IEventDataWithInheritableGenericArgument, IEventDataMayHaveTenantId {
    @Getter
    private TEntity entity;

    public EntityEventData(TEntity entity) {
        this.entity = entity;
    }

    @Override
    public Object[] getConstructorArgs() {
        return new Object[]{entity};
    }

    @Override
    public boolean isMultiTenant() {
        return entity instanceof IMultiTenant;
    }

    @Override
    public ID getTenantId() {
        if (entity instanceof IMultiTenant) {
            return ((IMultiTenant) entity).getTenantId();
        }
        return null;
    }
}

