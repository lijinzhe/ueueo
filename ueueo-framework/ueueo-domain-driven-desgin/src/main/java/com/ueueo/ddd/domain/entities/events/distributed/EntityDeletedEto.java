package com.ueueo.ddd.domain.entities.events.distributed;

import com.ueueo.ID;
import com.ueueo.eventbus.GenericEventNameAttribute;
import com.ueueo.eventbus.IEventDataMayHaveTenantId;
import com.ueueo.multitenancy.IMultiTenant;
import lombok.Data;

@GenericEventNameAttribute(postfix = ".Deleted")
@Data
public class EntityDeletedEto<TEntityEto> implements IEventDataMayHaveTenantId {
    private TEntityEto entity;

    public EntityDeletedEto(TEntityEto entity) {
        this.entity = entity;
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
