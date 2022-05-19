package com.ueueo.tenantmanagement.domain;

import com.ueueo.ID;
import com.ueueo.caching.IDistributedCache;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.multitenancy.ITenantStore;
import com.ueueo.multitenancy.TenantConfiguration;
import lombok.Getter;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 23:01
 */
@Getter
public class TenantStore implements ITenantStore {

    protected ITenantRepository tenantRepository;

    protected ICurrentTenant currentTenant;

    protected IDistributedCache<TenantCacheItem> cache;

    public TenantStore(ITenantRepository tenantRepository,
                       ICurrentTenant currentTenant,
                       IDistributedCache<TenantCacheItem> cache) {
        this.tenantRepository = tenantRepository;
        this.currentTenant = currentTenant;
        this.cache = cache;
    }

    @Override
    public TenantConfiguration find(String name) {
        return null;
    }

    @Override
    public TenantConfiguration find(ID id) {
        return null;
    }

}
