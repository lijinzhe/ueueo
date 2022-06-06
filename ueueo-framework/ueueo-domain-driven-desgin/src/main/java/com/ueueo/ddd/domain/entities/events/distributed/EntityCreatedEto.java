package com.ueueo.ddd.domain.entities.events.distributed;

import com.ueueo.ID;
import com.ueueo.eventbus.GenericEventNameAttribute;
import com.ueueo.eventbus.IEventDataMayHaveTenantId;
import com.ueueo.multitenancy.IMultiTenant;
import lombok.Data;

@GenericEventNameAttribute(postfix = ".Created")
@Data
public class EntityCreatedEto<TEntityEto> implements IEventDataMayHaveTenantId {
    private TEntityEto entity;

    public EntityCreatedEto(TEntityEto entity) {
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
