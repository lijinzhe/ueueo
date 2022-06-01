package com.ueueo.caching;

import lombok.Data;

import java.time.Duration;
import java.time.OffsetDateTime;

/**
 * Provides the cache options for an entry in Microsoft.Extensions.Caching.Distributed.IDistributedCache.
 *
 * @author Lee
 * @date 2022-05-29 14:42
 */
@Data
public class DistributedCacheEntryOptions {
    /**
     * Absolute expiration date for the cache entry.
     */
    private OffsetDateTime absoluteExpiration;
    /**
     * Absolute expiration time, relative to now.
     */
    private Duration absoluteExpirationRelativeToNow;

    /**
     * How long a cache entry can be inactive (e.g. not accessed) before
     * it will be removed. This will not extend the entry lifetime beyond the absolute
     * expiration (if set).
     */
    private Duration slidingExpiration;

}
