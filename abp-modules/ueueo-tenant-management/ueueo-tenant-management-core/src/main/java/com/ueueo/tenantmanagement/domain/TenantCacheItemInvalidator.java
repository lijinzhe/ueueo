package com.ueueo.tenantmanagement.domain;

import com.ueueo.ID;
import com.ueueo.caching.IDistributedCache;
import com.ueueo.ddd.domain.entities.events.EntityChangedEventData;
import com.ueueo.eventbus.local.ILocalEventHandler;
import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-20 14:19
 */
public class TenantCacheItemInvalidator implements ILocalEventHandler {
    @Getter
    protected final IDistributedCache<TenantCacheItem> cache;

    public TenantCacheItemInvalidator(IDistributedCache<TenantCacheItem> cache) {
        this.cache = cache;
    }

    @Override
    public void handleEvent(Object eventData) {
        if (eventData instanceof EntityChangedEventData) {
            ID tenantId = ((EntityChangedEventData<?>) eventData).getTenantId();
            if (tenantId != null) {
                cache.remove(TenantCacheItem.calculateCacheKey(tenantId, null), null, null);
            }
            if (((EntityChangedEventData<?>) eventData).getEntity() instanceof Tenant) {
                String name = ((Tenant) ((EntityChangedEventData<?>) eventData).getEntity()).getName();
                cache.remove(TenantCacheItem.calculateCacheKey(null, name), null, null);
            }
        }
    }
}
