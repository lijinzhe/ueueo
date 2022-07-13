package com.ueueo.caching;

import com.ueueo.multitenancy.ICurrentTenant;

/**
 * @author Lee
 * @date 2022-05-29 14:35
 */
public class DistributedCacheKeyNormalizer implements IDistributedCacheKeyNormalizer {
    protected ICurrentTenant currentTenant;

    protected AbpDistributedCacheOptions distributedCacheOptions;

    @Override
    public String normalizeKey(DistributedCacheKeyNormalizeArgs args) {
        String normalizedKey = String.format("c:%s,k:%s%s",
                args.getCacheName(), distributedCacheOptions.getKeyPrefix(), args.getKey());

        if (!args.isIgnoreMultiTenancy() && currentTenant.getId() != null) {
            normalizedKey = String.format("t:%s,%s", currentTenant.getId(), normalizedKey);
        }

        return normalizedKey;
    }

    public DistributedCacheKeyNormalizer(ICurrentTenant currentTenant, AbpDistributedCacheOptions distributedCacheOptions) {
        this.currentTenant = currentTenant;
        this.distributedCacheOptions = distributedCacheOptions;
    }

}
