package com.ueueo.tenantmanagement.domain;

import com.ueueo.exception.BaseException;
import com.ueueo.ID;
import com.ueueo.caching.IDistributedCache;
import com.ueueo.data.ConnectionStrings;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.multitenancy.ITenantStore;
import com.ueueo.multitenancy.TenantConfiguration;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2022-05-19 23:01
 */
@Getter
public class TenantStore implements ITenantStore {

    private final ITenantRepository tenantRepository;

    private final ICurrentTenant currentTenant;

    private final IDistributedCache<TenantCacheItem> cache;

    public TenantStore(ITenantRepository tenantRepository,
                       ICurrentTenant currentTenant,
                       IDistributedCache<TenantCacheItem> cache) {
        this.tenantRepository = tenantRepository;
        this.currentTenant = currentTenant;
        this.cache = cache;
    }

    @Override
    public TenantConfiguration find(String name) {
        return getCacheItem(null, name).getValue();
    }

    @Override
    public TenantConfiguration find(ID id) {
        return getCacheItem(id, null).getValue();
    }

    protected TenantCacheItem getCacheItem(ID id, String name) {
        String cacheKey = calculateCacheKey(id, name);
        TenantCacheItem cacheItem = cache.get(cacheKey,null,null);
        if (cacheItem != null) {
            return cacheItem;
        }
        if (id.hasValue()) {
            //TODO: No need this if we can implement to define host side (or tenant-independent) entities!
            currentTenant.change(null, null);
            Tenant tenant = tenantRepository.getById(id, false);
            return setCache(cacheKey, tenant);
        }
        if (StringUtils.isNotBlank(name)) {
            //TODO: No need this if we can implement to define host side (or tenant-independent) entities!
            currentTenant.change(null, null);
            Tenant tenant = tenantRepository.findByName(name, false);
            return setCache(cacheKey, tenant);
        }
        throw new BaseException("Both id and name can't be invalid.");
    }

    protected TenantCacheItem setCache(String cacheKey, @Nullable Tenant tenant) {
        TenantConfiguration tenantConfiguration = new TenantConfiguration();
        tenantConfiguration.setId(tenant.getId());
        tenantConfiguration.setName(tenant.getName());
        tenantConfiguration.setIsActive(true);
        tenantConfiguration.setConnectionStrings(new ConnectionStrings(tenant.getConnectionStrings().stream().collect(Collectors.toMap(TenantConnectionString::getName, TenantConnectionString::getValue))));

        TenantCacheItem cacheItem = new TenantCacheItem(tenantConfiguration);
        cache.set(cacheKey, cacheItem,null,null,null);
        return cacheItem;
    }

    protected String calculateCacheKey(ID id, String name) {
        return TenantCacheItem.calculateCacheKey(id, name);
    }
}
