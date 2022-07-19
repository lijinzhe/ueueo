package com.ueueo.caching;

import com.ueueo.KeyValuePair;
import org.springframework.cache.Cache;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a distributed cache of <typeparamref name="TCacheItem" /> type.
 *
 * @author Lee
 * @date 2021-08-23 14:01
 */
public interface IDistributedCache<TCacheItem> {

    /**
     * Gets a cache item with the given key. If no cache item is found for the given key then returns null.
     *
     * @param key         The key of cached item to be retrieved from the cache.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     *
     * @return The cache item, or null.
     */
    TCacheItem get(
            String key,
            Boolean hideErrors,
            Boolean considerUow
    );

    /**
     * Gets multiple cache items with the given keys.
     *
     * The returned list contains exactly the same count of items specified in the given keys.
     * An item in the return list can not be null, but an item in the list has null value
     * if the related key not found in the cache.
     *
     * @param keys        The keys of cached items to be retrieved from the cache.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     *
     * @return List of cache items.
     */
    List<KeyValuePair<String, TCacheItem>> getMany(
            List<String> keys,
            Boolean hideErrors,
            Boolean considerUow
    );

    /**
     * Gets or Adds a cache item with the given key. If no cache item is found for the given key then adds a cache item
     * provided by <paramref name="factory" /> delegate and returns the provided cache item.
     *
     * @param key            The key of cached item to be retrieved from the cache.
     * @param factory        The factory delegate is used to provide the cache item when no cache item is found for the given <paramref name="key" />.
     * @param optionsFactory The cache options for the factory delegate.
     * @param hideErrors     Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow    This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     *
     * @return The cache item.
     */
    TCacheItem getOrAdd(
            String key,
            Supplier<TCacheItem> factory,
            Supplier<DistributedCacheEntryOptions> optionsFactory,
            Boolean hideErrors,
            Boolean considerUow
    );

    /**
     * Gets or Adds multiple cache items with the given keys. If any cache items not found for the given keys then adds cache items
     * provided by <paramref name="factory" /> delegate and returns the provided cache items.
     *
     * @param keys           The keys of cached items to be retrieved from the cache.
     * @param factory        The factory delegate is used to provide the cache items when no cache items are found for the given <paramref name="keys" />.
     * @param optionsFactory The cache options for the factory delegate.
     * @param hideErrors     Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow    This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     *
     * @return The cache items.
     */
    List<KeyValuePair<String, TCacheItem>> getOrAddMany(
            List<String> keys,
            Function<List<String>, List<KeyValuePair<String, TCacheItem>>> factory,
            Supplier<DistributedCacheEntryOptions> optionsFactory,
            Boolean hideErrors,
            Boolean considerUow
    );

    /**
     * Sets the cache item value for the provided key.
     *
     * @param key         The key of cached item to be retrieved from the cache.
     * @param value       The cache item value to set in the cache.
     * @param options     The cache options for the value.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     */
    void set(
            String key,
            TCacheItem value,
            DistributedCacheEntryOptions options,
            Boolean hideErrors,
            Boolean considerUow
    );

    /**
     * Sets multiple cache items.
     * Based on the implementation, this can be more efficient than setting multiple items individually.
     *
     * @param items       Items to set on the cache
     * @param options     The cache options for the value.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     */
    void setMany(
            List<KeyValuePair<String, TCacheItem>> items,
            DistributedCacheEntryOptions options,
            Boolean hideErrors,
            Boolean considerUow
    );

    /**
     * Refreshes the cache value of the given key, and resets its sliding expiration timeout.
     *
     * @param key        The key of cached item to be retrieved from the cache.
     * @param hideErrors Indicates to throw or hide the exceptions for the distributed cache.
     */
    void refresh(
            String key,
            Boolean hideErrors
    );

    /**
     * Refreshes the cache value of the given keys, and resets their sliding expiration timeout.
     * Based on the implementation, this can be more efficient than setting multiple items individually.
     *
     * @param keys       The keys of cached items to be retrieved from the cache.
     * @param hideErrors Indicates to throw or hide the exceptions for the distributed cache.
     */
    void refreshMany(
            List<String> keys,
            Boolean hideErrors
    );

    /**
     * Removes the cache item for given key from cache.
     *
     * @param key         The key of cached item to be retrieved from the cache.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     */
    void remove(
            String key,
            Boolean hideErrors,
            Boolean considerUow
    );

    /**
     * Removes the cache items for given keys from cache.
     *
     * @param keys        The keys of cached items to be retrieved from the cache.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     */
    void removeMany(
            List<String> keys,
            Boolean hideErrors,
            Boolean considerUow
    );

}
