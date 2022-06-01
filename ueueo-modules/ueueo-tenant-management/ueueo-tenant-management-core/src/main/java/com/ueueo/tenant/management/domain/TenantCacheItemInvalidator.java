package com.ueueo.tenant.management.domain;

import com.ueueo.caching.IDistributedCache;
import com.ueueo.ddd.domain.entities.events.EntityChangedEventData;
import com.ueueo.eventbus.local.ILocalEventHandler;
import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-20 14:19
 */
public class TenantCacheItemInvalidator implements ILocalEventHandler<EntityChangedEventData<Tenant>> {
    @Getter
    protected final IDistributedCache<TenantCacheItem> cache;

    public TenantCacheItemInvalidator(IDistributedCache<TenantCacheItem> cache) {
        this.cache = cache;
    }

    @Override
    public void handleEvent(EntityChangedEventData<Tenant> eventData) {
        cache.remove(TenantCacheItem.calculateCacheKey(eventData.getEntity().getId(), null), null, null);
        cache.remove(TenantCacheItem.calculateCacheKey(null, eventData.getEntity().getName()), null, null);
    }
}
