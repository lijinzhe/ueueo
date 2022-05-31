using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.Caching.Distributed;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using Microsoft.Extensions.Options;
using Volo.Abp.ExceptionHandling;
using Volo.Abp.MultiTenancy;
using Volo.Abp.Threading;
using Volo.Abp.Uow;

namespace Volo.Abp.Caching;

/**
 * Represents a distributed cache of <typeparamref name="TCacheItem" /> type.
*/
 * <typeparam name="TCacheItem">The type of cache item being cached.</typeparam>
public class DistributedCache<TCacheItem> : DistributedCache<TCacheItem, String>, IDistributedCache<TCacheItem>
    //where TCacheItem : class
{
    public DistributedCache(
        IOptions<AbpDistributedCacheOptions> distributedCacheOption,
        IDistributedCache cache,
        ICancellationTokenProvider cancellationTokenProvider,
        IDistributedCacheSerializer serializer,
        IDistributedCacheKeyNormalizer keyNormalizer,
        IServiceScopeFactory serviceScopeFactory,
        IUnitOfWorkManager unitOfWorkManager) : base(
            distributedCacheOption: distributedCacheOption,
            cache: cache,
            cancellationTokenProvider: cancellationTokenProvider,
            serializer: serializer,
            keyNormalizer: keyNormalizer,
            serviceScopeFactory: serviceScopeFactory,
            unitOfWorkManager: unitOfWorkManager)
    {
    }
}

/**
 * Represents a distributed cache of <typeparamref name="TCacheItem" /> type.
 * Uses a generic cache key type of <typeparamref name="TCacheKey" /> type.
*/
 * <typeparam name="TCacheItem">The type of cache item being cached.</typeparam>
 * <typeparam name="TCacheKey">The type of cache key being used.</typeparam>
public class DistributedCache<TCacheItem, TCacheKey> : IDistributedCache<TCacheItem, TCacheKey>
    //where TCacheItem : class
{
    public const String UowCacheName = "AbpDistributedCache";

    public ILogger<DistributedCache<TCacheItem, TCacheKey>> Logger;// { get; set; }

    protected String CacheName;// { get; set; }

    protected boolean IgnoreMultiTenancy;// { get; set; }

    protected IDistributedCache Cache;//  { get; }

    protected ICancellationTokenProvider CancellationTokenProvider;//  { get; }

    protected IDistributedCacheSerializer Serializer;//  { get; }

    protected IDistributedCacheKeyNormalizer KeyNormalizer;//  { get; }

    protected IServiceScopeFactory ServiceScopeFactory;//  { get; }

    protected IUnitOfWorkManager UnitOfWorkManager;//  { get; }

    protected SemaphoreSlim SyncSemaphore;//  { get; }

    protected DistributedCacheEntryOptions DefaultCacheOptions;

    private readonly AbpDistributedCacheOptions _distributedCacheOption;

    public DistributedCache(
        IOptions<AbpDistributedCacheOptions> distributedCacheOption,
        IDistributedCache cache,
        ICancellationTokenProvider cancellationTokenProvider,
        IDistributedCacheSerializer serializer,
        IDistributedCacheKeyNormalizer keyNormalizer,
        IServiceScopeFactory serviceScopeFactory,
        IUnitOfWorkManager unitOfWorkManager)
    {
        _distributedCacheOption = distributedCacheOption.Value;
        Cache = cache;
        CancellationTokenProvider = cancellationTokenProvider;
        Logger = NullLogger<DistributedCache<TCacheItem, TCacheKey>>.Instance;
        Serializer = serializer;
        KeyNormalizer = keyNormalizer;
        ServiceScopeFactory = serviceScopeFactory;
        UnitOfWorkManager = unitOfWorkManager;

        SyncSemaphore = new SemaphoreSlim(1, 1);

        SetDefaultOptions();
    }

    protected   String NormalizeKey(TCacheKey key)
    {
        return KeyNormalizer.NormalizeKey(
            new DistributedCacheKeyNormalizeArgs(
                key.ToString(),
                CacheName,
                IgnoreMultiTenancy
            )
        );
    }

    protected   DistributedCacheEntryOptions GetDefaultCacheEntryOptions()
    {
        for (var configure in _distributedCacheOption.CacheConfigurators)
        {
            var options = configure.Invoke(CacheName);
            if (options != null)
            {
                return options;
            }
        }

        return _distributedCacheOption.GlobalCacheEntryOptions;
    }

    protected   void SetDefaultOptions()
    {
        CacheName = CacheNameAttribute.GetCacheName(typeof(TCacheItem));

        //IgnoreMultiTenancy
        IgnoreMultiTenancy = typeof(TCacheItem).IsDefined(typeof(IgnoreMultiTenancyAttribute), true);

        //Configure default cache entry options
        DefaultCacheOptions = GetDefaultCacheEntryOptions();
    }

    /**
     * Gets a cache item with the given key. If no cache item is found for the given key then returns null.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <returns>The cache item, or null.</returns>
     */
    public   TCacheItem Get(
        TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        if (ShouldConsiderUow(considerUow))
        {
            var value = GetUnitOfWorkCache().GetOrDefault(key)?.GetUnRemovedValueOrNull();
            if (value != null)
            {
                return value;
            }
        }

        byte[] cachedBytes;

        try
        {
            cachedBytes = Cache.Get(NormalizeKey(key));
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleException(ex);
                return null;
            }

            throw;
        }

        return ToCacheItem(cachedBytes);
    }

    public   KeyValuePair<TCacheKey, TCacheItem>[] GetMany(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        var keyArray = keys.ToArray();

        var cacheSupportsMultipleItems = Cache as ICacheSupportsMultipleItems;
        if (cacheSupportsMultipleItems == null)
        {
            return GetManyFallback(
                keyArray,
                hideErrors,
                considerUow
            );
        }

        var notCachedKeys = new List<TCacheKey>();
        var cachedValues = new List<KeyValuePair<TCacheKey, TCacheItem>>();
        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();
            for (var key in keyArray)
            {
                var value = uowCache.GetOrDefault(key)?.GetUnRemovedValueOrNull();
                if (value != null)
                {
                    cachedValues.Add(new KeyValuePair<TCacheKey, TCacheItem>(key, value));
                }
            }

            notCachedKeys = keyArray.Except(cachedValues.Select(x => x.Key)).ToList();
            if (!notCachedKeys.Any())
            {
                return cachedValues.ToArray();
            }
        }

        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;
        byte[][] cachedBytes;

        var readKeys = notCachedKeys.Any() ? notCachedKeys.ToArray() : keyArray;
        try
        {
            cachedBytes = cacheSupportsMultipleItems.GetMany(readKeys.Select(NormalizeKey));
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleException(ex);
                return ToCacheItemsWithDefaultValues(keyArray);
            }

            throw;
        }

        return cachedValues.Concat(ToCacheItems(cachedBytes, readKeys)).ToArray();
    }

    protected   KeyValuePair<TCacheKey, TCacheItem>[] GetManyFallback(
        TCacheKey[] keys,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            return keys
                .Select(key => new KeyValuePair<TCacheKey, TCacheItem>(
                        key,
                        Get(key, false, considerUow)
                    )
                ).ToArray();
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleException(ex);
                return ToCacheItemsWithDefaultValues(keys);
            }

            throw;
        }
    }

    public    KeyValuePair<TCacheKey, TCacheItem>[] GetManyAsync(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        var keyArray = keys.ToArray();

        var cacheSupportsMultipleItems = Cache as ICacheSupportsMultipleItems;
        if (cacheSupportsMultipleItems == null)
        {
            return GetManyFallbackAsync(
                keyArray,
                hideErrors,
                considerUow,
                token
            );
        }

        var notCachedKeys = new List<TCacheKey>();
        var cachedValues = new List<KeyValuePair<TCacheKey, TCacheItem>>();
        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();
            for (var key in keyArray)
            {
                var value = uowCache.GetOrDefault(key)?.GetUnRemovedValueOrNull();
                if (value != null)
                {
                    cachedValues.Add(new KeyValuePair<TCacheKey, TCacheItem>(key, value));
                }
            }

            notCachedKeys = keyArray.Except(cachedValues.Select(x => x.Key)).ToList();
            if (!notCachedKeys.Any())
            {
                return cachedValues.ToArray();
            }
        }

        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;
        byte[][] cachedBytes;

        var readKeys = notCachedKeys.Any() ? notCachedKeys.ToArray() : keyArray;

        try
        {
            cachedBytes = cacheSupportsMultipleItems.GetManyAsync(
                readKeys.Select(NormalizeKey),
                CancellationTokenProvider.FallbackToProvider(token)
            );
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleExceptionAsync(ex);
                return ToCacheItemsWithDefaultValues(keyArray);
            }

            throw;
        }

        return cachedValues.Concat(ToCacheItems(cachedBytes, readKeys)).ToArray();
    }

    protected    KeyValuePair<TCacheKey, TCacheItem>[] GetManyFallbackAsync(
        TCacheKey[] keys,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            var result = new List<KeyValuePair<TCacheKey, TCacheItem>>();

            for (var key in keys)
            {
                result.Add(new KeyValuePair<TCacheKey, TCacheItem>(
                    key,
                    GetAsync(key, false, considerUow, token: token))
                );
            }

            return result.ToArray();
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleExceptionAsync(ex);
                return ToCacheItemsWithDefaultValues(keys);
            }

            throw;
        }
    }

    /**
     * Gets a cache item with the given key. If no cache item is found for the given key then returns null.
    *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The cache item, or null.</returns>
     */
    public    TCacheItem GetAsync(
        TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        if (ShouldConsiderUow(considerUow))
        {
            var value = GetUnitOfWorkCache().GetOrDefault(key)?.GetUnRemovedValueOrNull();
            if (value != null)
            {
                return value;
            }
        }

        byte[] cachedBytes;

        try
        {
            cachedBytes = Cache.GetAsync(
                NormalizeKey(key),
                CancellationTokenProvider.FallbackToProvider(token)
            );
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleExceptionAsync(ex);
                return null;
            }

            throw;
        }

        if (cachedBytes == null)
        {
            return null;
        }

        return Serializer.Deserialize<TCacheItem>(cachedBytes);
    }

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
    public   TCacheItem GetOrAdd(
        TCacheKey key,
        Func<TCacheItem> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        var value = Get(key, hideErrors, considerUow);
        if (value != null)
        {
            return value;
        }

        using (SyncSemaphore.Lock())
        {
            value = Get(key, hideErrors, considerUow);
            if (value != null)
            {
                return value;
            }

            value = factory();

            if (ShouldConsiderUow(considerUow))
            {
                var uowCache = GetUnitOfWorkCache();
                if (uowCache.TryGetValue(key, out var item))
                {
                    item.SetValue(value);
                }
                else
                {
                    uowCache.Add(key, new UnitOfWorkCacheItem<TCacheItem>(value));
                }
            }

            Set(key, value, optionsFactory?.Invoke(), hideErrors, considerUow);
        }

        return value;
    }

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
    public    TCacheItem GetOrAddAsync(
        TCacheKey key,
        Func<TCacheItem> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        token = CancellationTokenProvider.FallbackToProvider(token);
        var value = GetAsync(key, hideErrors, considerUow, token);
        if (value != null)
        {
            return value;
        }

        using (SyncSemaphore.LockAsync(token))
        {
            value = GetAsync(key, hideErrors, considerUow, token);
            if (value != null)
            {
                return value;
            }

            value = factory();

            if (ShouldConsiderUow(considerUow))
            {
                var uowCache = GetUnitOfWorkCache();
                if (uowCache.TryGetValue(key, out var item))
                {
                    item.SetValue(value);
                }
                else
                {
                    uowCache.Add(key, new UnitOfWorkCacheItem<TCacheItem>(value));
                }
            }

            SetAsync(key, value, optionsFactory?.Invoke(), hideErrors, considerUow, token);
        }

        return value;
    }

    public KeyValuePair<TCacheKey, TCacheItem>[] GetOrAddMany(
        IEnumerable<TCacheKey> keys,
        Func<IEnumerable<TCacheKey>, List<KeyValuePair<TCacheKey, TCacheItem>>> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false)
    {

        KeyValuePair<TCacheKey, TCacheItem>[] result;
        var keyArray = keys.ToArray();

        var cacheSupportsMultipleItems = Cache as ICacheSupportsMultipleItems;
        if (cacheSupportsMultipleItems == null)
        {
            result = GetManyFallback(
                keyArray,
                hideErrors,
                considerUow
            );
        }
        else
        {
            var notCachedKeys = new List<TCacheKey>();
            var cachedValues = new List<KeyValuePair<TCacheKey, TCacheItem>>();
            if (ShouldConsiderUow(considerUow))
            {
                var uowCache = GetUnitOfWorkCache();
                for (var key in keyArray)
                {
                    var value = uowCache.GetOrDefault(key)?.GetUnRemovedValueOrNull();
                    if (value != null)
                    {
                        cachedValues.Add(new KeyValuePair<TCacheKey, TCacheItem>(key, value));
                    }
                }

                notCachedKeys = keyArray.Except(cachedValues.Select(x => x.Key)).ToList();
                if (!notCachedKeys.Any())
                {
                    return cachedValues.ToArray();
                }
            }

            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;
            byte[][] cachedBytes;

            var readKeys = notCachedKeys.Any() ? notCachedKeys.ToArray() : keyArray;
            try
            {
                cachedBytes = cacheSupportsMultipleItems.GetMany(readKeys.Select(NormalizeKey));
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleException(ex);
                    return ToCacheItemsWithDefaultValues(keyArray);
                }

                throw;
            }

            result = cachedValues.Concat(ToCacheItems(cachedBytes, readKeys)).ToArray();
        }

        if (result.All(x => x.Value != null))
        {
            return result;
        }

        var missingKeys = new List<TCacheKey>();
        var missingValuesIndex = new List<int>();
        for (var i = 0; i < keyArray.Length; i++)
        {
            if (result[i].Value != null)
            {
                continue;
            }

            missingKeys.Add(keyArray[i]);
            missingValuesIndex.Add(i);
        }

        var missingValues = factory.Invoke(missingKeys).ToArray();
        var valueQueue = new Queue<KeyValuePair<TCacheKey, TCacheItem>>(missingValues);

        SetMany(missingValues, optionsFactory?.Invoke(), hideErrors, considerUow);

        for (var index in missingValuesIndex)
        {
            result[index] = valueQueue.Dequeue();
        }

        return result;
    }


    public  KeyValuePair<TCacheKey, TCacheItem>[]> GetOrAddManyAsync(
        IEnumerable<TCacheKey> keys,
        Func<IEnumerable<TCacheKey>, List<KeyValuePair<TCacheKey, TCacheItem>>>> factory,
        Func<DistributedCacheEntryOptions> optionsFactory = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        KeyValuePair<TCacheKey, TCacheItem>[] result;
        var keyArray = keys.ToArray();

        var cacheSupportsMultipleItems = Cache as ICacheSupportsMultipleItems;
        if (cacheSupportsMultipleItems == null)
        {
            result = GetManyFallbackAsync(
                keyArray,
                hideErrors,
                considerUow, token);
        }
        else
        {
            var notCachedKeys = new List<TCacheKey>();
            var cachedValues = new List<KeyValuePair<TCacheKey, TCacheItem>>();
            if (ShouldConsiderUow(considerUow))
            {
                var uowCache = GetUnitOfWorkCache();
                for (var key in keyArray)
                {
                    var value = uowCache.GetOrDefault(key)?.GetUnRemovedValueOrNull();
                    if (value != null)
                    {
                        cachedValues.Add(new KeyValuePair<TCacheKey, TCacheItem>(key, value));
                    }
                }

                notCachedKeys = keyArray.Except(cachedValues.Select(x => x.Key)).ToList();
                if (!notCachedKeys.Any())
                {
                    return cachedValues.ToArray();
                }
            }

            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;
            byte[][] cachedBytes;

            var readKeys = notCachedKeys.Any() ? notCachedKeys.ToArray() : keyArray;
            try
            {
                cachedBytes = cacheSupportsMultipleItems.GetManyAsync(readKeys.Select(NormalizeKey), token);
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleExceptionAsync(ex);
                    return ToCacheItemsWithDefaultValues(keyArray);
                }

                throw;
            }

            result = cachedValues.Concat(ToCacheItems(cachedBytes, readKeys)).ToArray();
        }

        if (result.All(x => x.Value != null))
        {
            return result;
        }

        var missingKeys = new List<TCacheKey>();
        var missingValuesIndex = new List<int>();
        for (var i = 0; i < keyArray.Length; i++)
        {
            if (result[i].Value != null)
            {
                continue;
            }

            missingKeys.Add(keyArray[i]);
            missingValuesIndex.Add(i);
        }

        var missingValues = (factory.Invoke(missingKeys)).ToArray();
        var valueQueue = new Queue<KeyValuePair<TCacheKey, TCacheItem>>(missingValues);

        SetManyAsync(missingValues, optionsFactory?.Invoke(), hideErrors, considerUow, token);

        for (var index in missingValuesIndex)
        {
            result[index] = valueQueue.Dequeue();
        }

        return result;
    }

    /**
     * Sets the cache item value for the provided key.
    */
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="value">The cache item value to set in the cache.</param>
     * <param name="options">The cache options for the value.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
    public   void Set(
        TCacheKey key,
        TCacheItem value,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        void SetRealCache()
        {
            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

            try
            {
                Cache.Set(
                    NormalizeKey(key),
                    Serializer.Serialize(value),
                    options ?? DefaultCacheOptions
                );
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleException(ex);
                    return;
                }

                throw;
            }
        }

        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();
            if (uowCache.TryGetValue(key, out _))
            {
                uowCache[key].SetValue(value);
            }
            else
            {
                uowCache.Add(key, new UnitOfWorkCacheItem<TCacheItem>(value));
            }

            // ReSharper disable once PossibleNullReferenceException
            UnitOfWorkManager.Current.OnCompleted(() =>
            {
                SetRealCache();
                return Task.CompletedTask;
            });
        }
        else
        {
            SetRealCache();
        }
    }
    /**
     * Sets the cache item value for the provided key.
    */
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="value">The cache item value to set in the cache.</param>
     * <param name="options">The cache options for the value.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
    public   void SetAsync(
        TCacheKey key,
        TCacheItem value,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        void SetRealCache()
        {
            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

            try
            {
                Cache.SetAsync(
                    NormalizeKey(key),
                    Serializer.Serialize(value),
                    options ?? DefaultCacheOptions,
                    CancellationTokenProvider.FallbackToProvider(token)
                );
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleExceptionAsync(ex);
                    return;
                }

                throw;
            }
        }

        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();
            if (uowCache.TryGetValue(key, out _))
            {
                uowCache[key].SetValue(value);
            }
            else
            {
                uowCache.Add(key, new UnitOfWorkCacheItem<TCacheItem>(value));
            }

            // ReSharper disable once PossibleNullReferenceException
            UnitOfWorkManager.Current.OnCompleted(SetRealCache);
        }
        else
        {
            SetRealCache();
        }
    }

    public void SetMany(
        IEnumerable<KeyValuePair<TCacheKey, TCacheItem>> items,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        var itemsArray = items.ToArray();

        var cacheSupportsMultipleItems = Cache as ICacheSupportsMultipleItems;
        if (cacheSupportsMultipleItems == null)
        {
            SetManyFallback(
                itemsArray,
                options,
                hideErrors,
                considerUow
            );

            return;
        }

        void SetRealCache()
        {
            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

            try
            {
                cacheSupportsMultipleItems.SetMany(
                    ToRawCacheItems(itemsArray),
                    options ?? DefaultCacheOptions
                );
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleException(ex);
                    return;
                }

                throw;
            }
        }

        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();

            for (var pair in itemsArray)
            {
                if (uowCache.TryGetValue(pair.Key, out _))
                {
                    uowCache[pair.Key].SetValue(pair.Value);
                }
                else
                {
                    uowCache.Add(pair.Key, new UnitOfWorkCacheItem<TCacheItem>(pair.Value));
                }
            }

            // ReSharper disable once PossibleNullReferenceException
            UnitOfWorkManager.Current.OnCompleted(() =>
            {
                SetRealCache();
                return Task.CompletedTask;
            });
        }
        else
        {
            SetRealCache();
        }
    }

    protected   void SetManyFallback(
        KeyValuePair<TCacheKey, TCacheItem>[] items,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            for (var item in items)
            {
                Set(
                    item.Key,
                    item.Value,
                    options,
                    false,
                    considerUow
                );
            }
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleException(ex);
                return;
            }

            throw;
        }
    }

    public   void SetManyAsync(
        IEnumerable<KeyValuePair<TCacheKey, TCacheItem>> items,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        var itemsArray = items.ToArray();

        var cacheSupportsMultipleItems = Cache as ICacheSupportsMultipleItems;
        if (cacheSupportsMultipleItems == null)
        {
            SetManyFallbackAsync(
                itemsArray,
                options,
                hideErrors,
                considerUow,
                token
            );

            return;
        }

        void SetRealCache()
        {
            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

            try
            {
                cacheSupportsMultipleItems.SetManyAsync(
                    ToRawCacheItems(itemsArray),
                    options ?? DefaultCacheOptions,
                    CancellationTokenProvider.FallbackToProvider(token)
                );
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleExceptionAsync(ex);
                    return;
                }

                throw;
            }
        }

        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();

            for (var pair in itemsArray)
            {
                if (uowCache.TryGetValue(pair.Key, out _))
                {
                    uowCache[pair.Key].SetValue(pair.Value);
                }
                else
                {
                    uowCache.Add(pair.Key, new UnitOfWorkCacheItem<TCacheItem>(pair.Value));
                }
            }

            // ReSharper disable once PossibleNullReferenceException
            UnitOfWorkManager.Current.OnCompleted(SetRealCache);
        }
        else
        {
            SetRealCache();
        }
    }

    protected   void SetManyFallbackAsync(
        KeyValuePair<TCacheKey, TCacheItem>[] items,
        DistributedCacheEntryOptions options = null,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            for (var item in items)
            {
                SetAsync(
                    item.Key,
                    item.Value,
                    options,
                    false,
                    considerUow,
                    token: token
                );
            }
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleExceptionAsync(ex);
                return;
            }

            throw;
        }
    }

    /**
     * Refreshes the cache value of the given key, and resets its sliding expiration timeout.
    */
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
    public   void Refresh(
        TCacheKey key,
        boolean hideErrors = null)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            Cache.Refresh(NormalizeKey(key));
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleException(ex);
                return;
            }

            throw;
        }
    }

    /**
     * Refreshes the cache value of the given key, and resets its sliding expiration timeout.
    */
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
    public   void RefreshAsync(
        TCacheKey key,
        boolean hideErrors = null,
        CancellationToken token = default)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            Cache.RefreshAsync(NormalizeKey(key), CancellationTokenProvider.FallbackToProvider(token));
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleExceptionAsync(ex);
                return;
            }

            throw;
        }
    }

    public   void RefreshMany(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            if (Cache is ICacheSupportsMultipleItems cacheSupportsMultipleItems)
            {
                cacheSupportsMultipleItems.RefreshMany(keys.Select(NormalizeKey));
            }
            else
            {
                for (var key in keys)
                {
                    Cache.Refresh(NormalizeKey(key));
                }
            }
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleException(ex);
                return;
            }

            throw;
        }
    }

    public   void RefreshManyAsync(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        CancellationToken token = default)
    {
        hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

        try
        {
            if (Cache is ICacheSupportsMultipleItems cacheSupportsMultipleItems)
            {
                cacheSupportsMultipleItems.RefreshManyAsync(keys.Select(NormalizeKey), token);
            }
            else
            {
                for (var key in keys)
                {
                    Cache.RefreshAsync(NormalizeKey(key), token);
                }
            }
        }
        catch (Exception ex)
        {
            if (hideErrors == true)
            {
                HandleExceptionAsync(ex);
                return;
            }

            throw;
        }
    }

    /**
     * Removes the cache item for given key from cache.
    */
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
    public   void Remove(
        TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        void RemoveRealCache()
        {
            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

            try
            {
                Cache.Remove(NormalizeKey(key));
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleException(ex);
                    return;
                }

                throw;
            }
        }

        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();
            if (uowCache.TryGetValue(key, out _))
            {
                uowCache[key].RemoveValue();
            }

            // ReSharper disable once PossibleNullReferenceException
            UnitOfWorkManager.Current.OnCompleted(() =>
            {
                RemoveRealCache();
                return Task.CompletedTask;
            });
        }
        else
        {
            RemoveRealCache();
        }
    }

    /**
     * Removes the cache item for given key from cache.
    */
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="token">The <see cref="T:System.Threading.CancellationToken" /> for the task.</param>
     * <returns>The <see cref="T:System.Threading.Tasks.Task" /> indicating that the operation is asynchronous.</returns>
    public   void RemoveAsync(
        TCacheKey key,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        void RemoveRealCache()
        {
            hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

            try
            {
                Cache.RemoveAsync(NormalizeKey(key), CancellationTokenProvider.FallbackToProvider(token));
            }
            catch (Exception ex)
            {
                if (hideErrors == true)
                {
                    HandleExceptionAsync(ex);
                    return;
                }

                throw;
            }
        }

        if (ShouldConsiderUow(considerUow))
        {
            var uowCache = GetUnitOfWorkCache();
            if (uowCache.TryGetValue(key, out _))
            {
                uowCache[key].RemoveValue();
            }

            // ReSharper disable once PossibleNullReferenceException
            UnitOfWorkManager.Current.OnCompleted(RemoveRealCache);
        }
        else
        {
            RemoveRealCache();
        }
    }

    public void RemoveMany(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false)
    {
        var keyArray = keys.ToArray();

        if (Cache is ICacheSupportsMultipleItems cacheSupportsMultipleItems)
        {
            void RemoveRealCache()
            {
                hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

                try
                {
                    cacheSupportsMultipleItems.RemoveMany(
                        keyArray.Select(NormalizeKey)
                    );
                }
                catch (Exception ex)
                {
                    if (hideErrors == true)
                    {
                        HandleException(ex);
                        return;
                    }

                    throw;
                }
            }

            if (ShouldConsiderUow(considerUow))
            {
                var uowCache = GetUnitOfWorkCache();

                for (var key in keyArray)
                {
                    if (uowCache.TryGetValue(key, out _))
                    {
                        uowCache[key].RemoveValue();
                    }
                }

                // ReSharper disable once PossibleNullReferenceException
                UnitOfWorkManager.Current.OnCompleted(() =>
                {
                    RemoveRealCache();
                    return Task.CompletedTask;
                });
            }
            else
            {
                RemoveRealCache();
            }
        }
        else
        {
            for (var key in keyArray)
            {
                Remove(key, hideErrors, considerUow);
            }
        }
    }

    public void RemoveManyAsync(
        IEnumerable<TCacheKey> keys,
        boolean hideErrors = null,
        boolean considerUow = false,
        CancellationToken token = default)
    {
        var keyArray = keys.ToArray();

        if (Cache is ICacheSupportsMultipleItems cacheSupportsMultipleItems)
        {
            void RemoveRealCache()
            {
                hideErrors = hideErrors ?? _distributedCacheOption.HideErrors;

                try
                {
                    cacheSupportsMultipleItems.RemoveManyAsync(
                        keyArray.Select(NormalizeKey), token);
                }
                catch (Exception ex)
                {
                    if (hideErrors == true)
                    {
                        HandleExceptionAsync(ex);
                        return;
                    }

                    throw;
                }
            }

            if (ShouldConsiderUow(considerUow))
            {
                var uowCache = GetUnitOfWorkCache();

                for (var key in keyArray)
                {
                    if (uowCache.TryGetValue(key, out _))
                    {
                        uowCache[key].RemoveValue();
                    }
                }

                // ReSharper disable once PossibleNullReferenceException
                UnitOfWorkManager.Current.OnCompleted(RemoveRealCache);
            }
            else
            {
                RemoveRealCache();
            }
        }
        else
        {
            for (var key in keyArray)
            {
                RemoveAsync(key, hideErrors, considerUow, token);
            }
        }
    }

    protected   void HandleException(Exception ex)
    {
        _ = HandleExceptionAsync(ex);
    }

    protected   void HandleExceptionAsync(Exception ex)
    {
        Logger.LogException(ex, LogLevel.Warning);

        using (var scope = ServiceScopeFactory.CreateScope())
        {
            scope.ServiceProvider
                .GetRequiredService<IExceptionNotifier>()
                .NotifyAsync(new ExceptionNotificationContext(ex, LogLevel.Warning));
        }
    }

    protected   KeyValuePair<TCacheKey, TCacheItem>[] ToCacheItems(byte[][] itemBytes, TCacheKey[] itemKeys)
    {
        if (itemBytes.Length != itemKeys.Length)
        {
            throw new AbpException("count of the item bytes should be same with the count of the given keys");
        }

        var result = new List<KeyValuePair<TCacheKey, TCacheItem>>();

        for (int i = 0; i < itemKeys.Length; i++)
        {
            result.Add(
                new KeyValuePair<TCacheKey, TCacheItem>(
                    itemKeys[i],
                    ToCacheItem(itemBytes[i])
                )
            );
        }

        return result.ToArray();
    }

    [CanBeNull]
    protected   TCacheItem ToCacheItem(@Nullable byte[] bytes)
    {
        if (bytes == null)
        {
            return null;
        }

        return Serializer.Deserialize<TCacheItem>(bytes);
    }


    protected   KeyValuePair<String, byte[]>[] ToRawCacheItems(KeyValuePair<TCacheKey, TCacheItem>[] items)
    {
        return items
            .Select(i => new KeyValuePair<String, byte[]>(
                    NormalizeKey(i.Key),
                    Serializer.Serialize(i.Value)
                )
            ).ToArray();
    }

    private static KeyValuePair<TCacheKey, TCacheItem>[] ToCacheItemsWithDefaultValues(TCacheKey[] keys)
    {
        return keys
            .Select(key => new KeyValuePair<TCacheKey, TCacheItem>(key, default))
            .ToArray();
    }

    protected   boolean ShouldConsiderUow(boolean considerUow)
    {
        return considerUow && UnitOfWorkManager.Current != null;
    }

    protected   String GetUnitOfWorkCacheKey()
    {
        return UowCacheName + CacheName;
    }

    protected   Dictionary<TCacheKey, UnitOfWorkCacheItem<TCacheItem>> GetUnitOfWorkCache()
    {
        if (UnitOfWorkManager.Current == null)
        {
            throw new AbpException($"There is no active UOW.");
        }

        return UnitOfWorkManager.Current.GetOrAddItem(GetUnitOfWorkCacheKey(),
            key => new Dictionary<TCacheKey, UnitOfWorkCacheItem<TCacheItem>>());
    }
}
