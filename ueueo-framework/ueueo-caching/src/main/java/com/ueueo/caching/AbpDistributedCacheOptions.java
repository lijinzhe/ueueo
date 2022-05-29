package com.ueueo.caching;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-29 14:36
 */
@Data
public class AbpDistributedCacheOptions {

    /**
     * Throw or hide exceptions for the distributed cache.
     */
    private boolean hideErrors = true;

    /**
     * Cache key prefix.
     */
    private String keyPrefix;

    /**
     * Gets or sets an absolute expiration date for the cache entry.
     */
    private DistributedCacheEntryOptions globalCacheEntryOptions;

    /**
     * List of all cache configurators.
     * (func argument:Name of cache)
     */
    private List<Function<String, DistributedCacheEntryOptions>> cacheConfigurators; //TODO: use a configurator interface instead?

    public AbpDistributedCacheOptions() {
        cacheConfigurators = new ArrayList<>();
        globalCacheEntryOptions = new DistributedCacheEntryOptions();
        keyPrefix = "";
    }
}
