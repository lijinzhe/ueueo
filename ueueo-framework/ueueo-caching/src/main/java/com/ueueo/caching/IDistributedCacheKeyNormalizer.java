package com.ueueo.caching;

/**
 * @author Lee
 * @date 2022-05-29 14:20
 */
public interface IDistributedCacheKeyNormalizer {
    String normalizeKey(DistributedCacheKeyNormalizeArgs args);
}
