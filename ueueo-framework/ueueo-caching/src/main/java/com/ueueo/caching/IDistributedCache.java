package com.ueueo.caching;

import com.ueueo.core.KeyValuePair;

import java.util.List;
import java.util.function.Consumer;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-23 14:01
 */
public interface IDistributedCache<TCacheItem> {

    default TCacheItem get(String key) {
        return get(key, null, false);
    }

    /**
     * Gets a cache item with the given key. If no cache item is found for the given key then returns null.
     *
     * @param key         The key of cached item to be retrieved from the cache.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     *
     * @return The cache item, or null.
     */
    TCacheItem get(String key, Boolean hideErrors, Boolean considerUow);

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
    List<KeyValuePair<String, TCacheItem>> getMany(List<String> keys, Boolean hideErrors, Boolean considerUow);

    /// <summary>
    /// Gets or Adds a cache item with the given key. If no cache item is found for the given key then adds a cache item
    /// provided by <paramref name="factory" /> delegate and returns the provided cache item.
    /// </summary>
    /// <param name="key">The key of cached item to be retrieved from the cache.</param>
    /// <param name="factory">The factory delegate is used to provide the cache item when no cache item is found for the given <paramref name="key" />.</param>
    /// <param name="optionsFactory">The cache options for the factory delegate.</param>
    /// <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
    /// <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
    /// <returns>The cache item.</returns>

    /**
     * Gets or Adds a cache item with the given key. If no cache item is found for the given key then adds a cache item
     * provided by <paramref name="factory" /> delegate and returns the provided cache item.
     *
     * @param key         The key of cached item to be retrieved from the cache.
     * @param factory     The factory delegate is used to provide the cache item when no cache item is found for the given <paramref name="key" />.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     *
     * @return The cache item.
     */
    TCacheItem getOrAdd(String key, Consumer<TCacheItem> factory, Boolean hideErrors, Boolean considerUow);

    /// <summary>
    /// Gets or Adds multiple cache items with the given keys. If any cache items not found for the given keys then adds cache items
    /// provided by <paramref name="factory" /> delegate and returns the provided cache items.
    /// </summary>
    /// <param name="keys">The keys of cached items to be retrieved from the cache.</param>
    /// <param name="factory">The factory delegate is used to provide the cache items when no cache items are found for the given <paramref name="keys" />.</param>
    /// <param name="optionsFactory">The cache options for the factory delegate.</param>
    /// <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
    /// <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
    /// <returns>The cache items.</returns>
    //    KeyValuePair<String, TCacheItem>[] getOrAddMany(
    //            Collection<String> keys,
    //            Func<IEnumerable<TCacheKey>, List<KeyValuePair<TCacheKey, TCacheItem>>> factory,
    //            Boolean hideErrors,
    //            Boolean considerUow
    //    );

    default void set(String key, TCacheItem value) {
        set(key, value, null, false);
    }

    /**
     * Sets the cache item value for the provided key.
     *
     * @param key         The key of cached item to be retrieved from the cache.
     * @param value       The cache item value to set in the cache.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow
     */
    void set(String key, TCacheItem value, Boolean hideErrors, Boolean considerUow);

    /**
     * Sets multiple cache items.
     * Based on the implementation, this can be more efficient than setting multiple items individually.
     *
     * @param items       Items to set on the cache
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     */
    void setMany(List<KeyValuePair<String, TCacheItem>> items, Boolean hideErrors, Boolean considerUow);

    /**
     * Refreshes the cache value of the given key, and resets its sliding expiration timeout.
     *
     * @param key        The key of cached item to be retrieved from the cache.
     * @param hideErrors Indicates to throw or hide the exceptions for the distributed cache.
     */
    void refresh(String key, Boolean hideErrors);

    /// <summary>
    /// Refreshes the cache value of the given keys, and resets their sliding expiration timeout.
    /// Based on the implementation, this can be more efficient than setting multiple items individually.
    /// </summary>
    /// <param name="keys">The keys of cached items to be retrieved from the cache.</param>
    /// <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
    //    void RefreshMany(
    //            Collection<String> keys,
    //            Boolean hideErrors = null);

    default void remove(String key) {
        remove(key, null, false);
    }

    /**
     * Removes the cache item for given key from cache.
     *
     * @param key         The key of cached item to be retrieved from the cache.
     * @param hideErrors  Indicates to throw or hide the exceptions for the distributed cache.
     * @param considerUow This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.
     */
    void remove(String key, Boolean hideErrors, Boolean considerUow);

    /// <summary>
    /// Removes the cache items for given keys from cache.
    /// </summary>
    /// <param name="keys">The keys of cached items to be retrieved from the cache.</param>
    /// <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
    /// <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
    //    void RemoveMany(
    //            Collection<String> keys,
    //            Boolean hideErrors =null,
    //            Boolean considerUow =false
    //    );

}
