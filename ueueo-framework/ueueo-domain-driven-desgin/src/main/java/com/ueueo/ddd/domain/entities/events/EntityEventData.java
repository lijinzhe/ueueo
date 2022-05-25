package com.ueueo.ddd.domain.entities.events;

import com.ueueo.ID;
import com.ueueo.eventbus.IEventDataMayHaveTenantId;
import com.ueueo.eventbus.IEventDataWithInheritableGenericArgument;
import com.ueueo.multitenancy.IMultiTenant;
import lombok.Getter;

/**
 * Used to pass data for an event that is related to with an <see cref="IEntity"/> object.
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

