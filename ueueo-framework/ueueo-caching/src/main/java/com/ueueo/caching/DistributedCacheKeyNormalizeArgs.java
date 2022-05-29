package com.ueueo.caching;

import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-29 14:20
 */
@Getter
public class DistributedCacheKeyNormalizeArgs {
    private String key;

    private String cacheName;

    private boolean ignoreMultiTenancy;

    public DistributedCacheKeyNormalizeArgs(String key, String cacheName, boolean ignoreMultiTenancy) {
        this.key = key;
        this.cacheName = cacheName;
        this.ignoreMultiTenancy = ignoreMultiTenancy;
    }
}
