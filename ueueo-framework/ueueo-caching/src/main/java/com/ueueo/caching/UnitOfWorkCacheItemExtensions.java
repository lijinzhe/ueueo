package com.ueueo.caching;

/**
 * @author Lee
 * @date 2022-05-29 14:24
 */
public class UnitOfWorkCacheItemExtensions {
    public static <TValue extends Class<?>> TValue getUnRemovedValueOrNull(UnitOfWorkCacheItem<TValue> item) {
        return item != null && !item.isRemoved ? item.getValue() : null;
    }
}
