using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.Caching.Distributed;

namespace Volo.Abp.Caching;

/**
 * Represents a distributed cache of <typeparamref name="TCacheItem" /> type.
*
 * <typeparam name="TCacheItem">The type of cache item being cached.</typeparam>
     */
public interface IDistributedCache<TCacheItem> : IDistributedCache<TCacheItem, String>
    //where TCacheItem : class
{
}

/**
 * Represents a distributed cache of <typeparamref name="TCacheItem" /> type.
 * Uses a generic cache key type of <typeparamref name="TCacheKey" /> type.
*
 * <typeparam name="TCacheItem">The type of cache item being cached.</typeparam>
 * <typeparam name="TCacheKey">The type of cache key being used.</typeparam>
     */
public interface IDistributedCache<TCacheItem, TCacheKey>
    //where TCacheItem : class
{
    /**
     * Gets a cache item with the given key. If no cache item is found for the given key then returns null.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <returns>The cache item, or null.</returns>
     */
    TCacheItem Get(
        TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Gets multiple cache items with the given keys.
     *
     * The returned list contains exactly the same count of items specified in the given keys.
     * An item in the return list can not be null, but an item in the list has null value
     * if the related key not found in the cache.
    *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <returns>List of cache items.</returns>
     */
    KeyValuePair<TCacheKey, TCacheItem>[] GetMany(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Gets multiple cache items with the given keys.
     *
     * The returned list contains exactly the same count of items specified in the given keys.
     * An item in the return list can not be null, but an item in the list has null value
     * if the related key not found in the cache.
     *
     *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     *  * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>List of cache items.</returns>
     */
    KeyValuePair<TCacheKey, TCacheItem>[]> GetManyAsync(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );

    /**
     * Gets a cache item with the given key. If no cache item is found for the given key then returns null.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The cache item, or null.</returns>
     */
    TCacheItem> GetAsync(
        @NonNull TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );

    /**
     * Gets or Adds a cache item with the given key. If no cache item is found for the given key then adds a cache item
     * provided by <paramref name="factory" /> delegate and returns the provided cache item.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="factory">The factory delegate is used to provide the cache item when no cache item is found for the given <paramref name="key" />.</param>
     * <param name="optionsFactory">The cache options for the factory delegate.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <returns>The cache item.</returns>
     */
    TCacheItem GetOrAdd(
        TCacheKey key,
        Func<TCacheItem> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Gets or Adds a cache item with the given key. If no cache item is found for the given key then adds a cache item
     * provided by <paramref name="factory" /> delegate and returns the provided cache item.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="factory">The factory delegate is used to provide the cache item when no cache item is found for the given <paramref name="key" />.</param>
     * <param name="optionsFactory">The cache options for the factory delegate.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The cache item.</returns>
     */
    TCacheItem> GetOrAddAsync(
        @NonNull TCacheKey key,
        Func<TCacheItem>> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );

    /**
     * Gets or Adds multiple cache items with the given keys. If any cache items not found for the given keys then adds cache items
     * provided by <paramref name="factory" /> delegate and returns the provided cache items.
    *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="factory">The factory delegate is used to provide the cache items when no cache items are found for the given <paramref name="keys" />.</param>
     * <param name="optionsFactory">The cache options for the factory delegate.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <returns>The cache items.</returns>
     */
    KeyValuePair<TCacheKey, TCacheItem>[] GetOrAddMany(
        IEnumerable<TCacheKey> keys,
        Func<IEnumerable<TCacheKey>, List<KeyValuePair<TCacheKey, TCacheItem>>> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Gets or Adds multiple cache items with the given keys. If any cache items not found for the given keys then adds cache items
     * provided by <paramref name="factory" /> delegate and returns the provided cache items.
    *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="factory">The factory delegate is used to provide the cache items when no cache items are found for the given <paramref name="keys" />.</param>
     * <param name="optionsFactory">The cache options for the factory delegate.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The cache items.</returns>
     */
    KeyValuePair<TCacheKey, TCacheItem>[]> GetOrAddManyAsync(
        IEnumerable<TCacheKey> keys,
        Func<IEnumerable<TCacheKey>, List<KeyValuePair<TCacheKey, TCacheItem>>>> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );

    /**
     * Sets the cache item value for the provided key.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="value">The cache item value to set in the cache.</param>
     * <param name="options">The cache options for the value.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     */
    void Set(
        TCacheKey key,
        TCacheItem value,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Sets the cache item value for the provided key.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="value">The cache item value to set in the cache.</param>
     * <param name="options">The cache options for the value.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
     */
    void SetAsync(
        @NonNull TCacheKey key,
        @NonNull TCacheItem value,
        @Nullable DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );

    /**
     * Sets multiple cache items.
     * Based on the implementation, this can be more efficient than setting multiple items individually.
    *
     * <param name="items">Items to set on the cache</param>
     * <param name="options">The cache options for the value.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     */
    void SetMany(
        IEnumerable<KeyValuePair<TCacheKey, TCacheItem>> items,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Sets multiple cache items.
     * Based on the implementation, this can be more efficient than setting multiple items individually.
    *
     * <param name="items">Items to set on the cache</param>
     * <param name="options">The cache options for the value.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
     */
    void SetManyAsync(
        IEnumerable<KeyValuePair<TCacheKey, TCacheItem>> items,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );

    /**
     * Refreshes the cache value of the given key, and resets its sliding expiration timeout.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     */
    void Refresh(
        TCacheKey key,
        boolean hideErrors = null
    );

    /**
     * Refreshes the cache value of the given key, and resets its sliding expiration timeout.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
     */
    void RefreshAsync(
        TCacheKey key,
        boolean hideErrors = null,
        CancellationToken token = default
    );

    /**
     * Refreshes the cache value of the given keys, and resets their sliding expiration timeout.
     * Based on the implementation, this can be more efficient than setting multiple items individually.
    *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     */
    void RefreshMany(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null);

    /**
     * Refreshes the cache value of the given keys, and resets their sliding expiration timeout.
     * Based on the implementation, this can be more efficient than setting multiple items individually.
    *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
     */
    void RefreshManyAsync(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        CancellationToken token = default);

    /**
     * Removes the cache item for given key from cache.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     */
    void Remove(
        TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Removes the cache item for given key from cache.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
     */
    void RemoveAsync(
        TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );

    /**
     * Removes the cache items for given keys from cache.
    *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     */
    void RemoveMany(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false
    );

    /**
     * Removes the cache items for given keys from cache.
    *
     * <param name="keys">The keys of cached items to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
     */
    void RemoveManyAsync(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default
    );
}
