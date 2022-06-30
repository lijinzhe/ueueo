package com.ueueo.caching;

/**
 * Represents a distributed cache of <typeparamref name="TCacheItem" /> type.
 *
 * <typeparam name="TCacheItem">The type of cache item being cached.</typeparam>
 */

import com.ueueo.SystemException;
import com.ueueo.KeyValuePair;
import com.ueueo.exceptionhandling.ExceptionNotificationContext;
import com.ueueo.exceptionhandling.IExceptionNotifier;
import com.ueueo.multitenancy.IgnoreMultiTenancyAttribute;
import com.ueueo.uow.IUnitOfWorkManager;
import com.ueueo.uow.UnitOfWorkExtensions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.event.Level;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Represents a distributed cache of <typeparamref name="TCacheItem" /> type.
 * Uses a generic cache key type of <typeparamref name="String" /> type.
 *
 * <typeparam name="TCacheItem">The type of cache item being cached.</typeparam>
 * <typeparam name="String">The type of cache key being used.</typeparam>
 */
@Slf4j
public class DistributedCache<TCacheItem> implements IDistributedCache<TCacheItem> {
    public final String UowCacheName = "AbpDistributedCache";

    private final Class<TCacheItem> cacheItemType;

    protected String cacheName;

    protected boolean ignoreMultiTenancy;

    protected Cache cache;

    protected IDistributedCacheSerializer serializer;

    protected IDistributedCacheKeyNormalizer keyNormalizer;

    protected BeanFactory beanFactory;

    protected IUnitOfWorkManager unitOfWorkManager;

    private final Object Lock = new Object();

    protected DistributedCacheEntryOptions defaultCacheOptions;

    private AbpDistributedCacheOptions distributedCacheOption;

    private IExceptionNotifier exceptionNotifier;

    public DistributedCache(
            Class<TCacheItem> cacheItemType,
            AbpDistributedCacheOptions distributedCacheOption,
            Cache cache,
            IDistributedCacheSerializer serializer,
            IDistributedCacheKeyNormalizer keyNormalizer,
            BeanFactory beanFactory,
            IUnitOfWorkManager unitOfWorkManager,
            IExceptionNotifier exceptionNotifier
    ) {
        this.cacheItemType = cacheItemType;
        this.distributedCacheOption = distributedCacheOption;
        this.cache = cache;
        this.serializer = serializer;
        this.keyNormalizer = keyNormalizer;
        this.beanFactory = beanFactory;
        this.unitOfWorkManager = unitOfWorkManager;
        this.exceptionNotifier = exceptionNotifier;

        setDefaultOptions();
    }

    protected String normalizeKey(String key) {
        return keyNormalizer.normalizeKey(
                new DistributedCacheKeyNormalizeArgs(
                        key,
                        cacheName,
                        ignoreMultiTenancy
                )
        );
    }

    protected DistributedCacheEntryOptions getDefaultCacheEntryOptions() {
        for (Function<String, DistributedCacheEntryOptions> configure : distributedCacheOption.getCacheConfigurators()) {
            DistributedCacheEntryOptions options = configure.apply(cacheName);
            if (options != null) {
                return options;
            }
        }

        return distributedCacheOption.getGlobalCacheEntryOptions();
    }

    protected void setDefaultOptions() {
        CacheNameAttribute attribute = cacheItemType.getAnnotation(CacheNameAttribute.class);
        if (attribute != null) {
            cacheName = attribute.name();
        } else {
            cacheName = StringUtils.removeEnd(cacheItemType.getName(), "CacheItem");
        }

        //IgnoreMultiTenancy
        ignoreMultiTenancy = cacheItemType.isAnnotationPresent(IgnoreMultiTenancyAttribute.class);

        //Configure default cache entry options
        defaultCacheOptions = getDefaultCacheEntryOptions();
    }

    /**
     * Gets a cache item with the given key. If no cache item is found for the given key then returns null.
     *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <returns>The cache item, or null.</returns>
     */
    @Override
    public TCacheItem get(
            String key,
            Boolean hideErrors,
            Boolean considerUow) {
        hideErrors = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();

        if (shouldConsiderUow(considerUow)) {
            UnitOfWorkCacheItem<TCacheItem> cacheItem = GetUnitOfWorkCache().get(key);
            TCacheItem value = cacheItem.getUnRemovedValueOrNull();
            if (value != null) {
                return value;
            }
        }

        //        byte[] cachedBytes;

        try {
            return cache.get(normalizeKey(key), cacheItemType);
        } catch (Exception ex) {
            if (hideErrors) {
                handleException(ex);
                return null;
            }

            throw ex;
        }

    }

    @Override
    public List<KeyValuePair<String, TCacheItem>> getMany(
            List<String> keys,
            Boolean hideErrors,
            Boolean considerUow) {
        if (!(cache instanceof ICacheSupportsMultipleItems)) {
            return getManyFallback(
                    keys,
                    hideErrors,
                    considerUow
            );
        }

        List<String> notCachedKeys = new ArrayList<>();
        List<KeyValuePair<String, TCacheItem>> cachedValues = new ArrayList<>();
        if (shouldConsiderUow(considerUow)) {
            Map<String, UnitOfWorkCacheItem<TCacheItem>> uowCache = GetUnitOfWorkCache();
            for (String key : keys) {
                UnitOfWorkCacheItem<TCacheItem> cacheItem = uowCache.getOrDefault(key, null);
                if (cacheItem != null) {
                    TCacheItem value = cacheItem.getUnRemovedValueOrNull();
                    if (value != null) {
                        cachedValues.add(new KeyValuePair<>(key, value));
                    }
                }

            }
            Set<String> cachedKeys = cachedValues.stream().map(KeyValuePair::getKey).collect(Collectors.toSet());
            notCachedKeys = keys.stream().filter(x -> !cachedKeys.contains(x)).collect(Collectors.toList());
            if (notCachedKeys.isEmpty()) {
                return cachedValues;
            }
        }

        hideErrors = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
        byte[][] cachedBytes;

        List<String> readKeys = !notCachedKeys.isEmpty() ? notCachedKeys : keys;
        try {
            cachedBytes = ((ICacheSupportsMultipleItems) cache).getMany(readKeys.stream().map(this::normalizeKey).collect(Collectors.toList()));
        } catch (Exception ex) {
            if (hideErrors) {
                handleException(ex);
                return toCacheItemsWithDefaultValues(keys);
            }

            throw ex;
        }

        cachedValues.addAll(toCacheItems(cachedBytes, readKeys));
        return cachedValues;
    }

    protected List<KeyValuePair<String, TCacheItem>> getManyFallback(
            List<String> keys,
            Boolean hideErrors,
            Boolean considerUow) {
        hideErrors = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();

        try {
            return keys.stream().map(key -> new KeyValuePair<>(key, get(key, false, considerUow)))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            if (hideErrors) {
                handleException(ex);
                return toCacheItemsWithDefaultValues(keys);
            }

            throw ex;
        }
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
    @Override
    public TCacheItem getOrAdd(
            String key,
            Supplier<TCacheItem> factory,
            Supplier<DistributedCacheEntryOptions> optionsFactory,
            Boolean hideErrors,
            Boolean considerUow) {
        TCacheItem value = get(key, hideErrors, considerUow);
        if (value != null) {
            return value;
        }

        synchronized (Lock) {
            value = get(key, hideErrors, considerUow);
            if (value != null) {
                return value;
            }

            value = factory.get();

            if (shouldConsiderUow(considerUow)) {
                Map<String, UnitOfWorkCacheItem<TCacheItem>> uowCache = GetUnitOfWorkCache();
                UnitOfWorkCacheItem<TCacheItem> cacheItem = uowCache.get(key);
                if (cacheItem != null) {
                    cacheItem.setValue(value);
                } else {
                    uowCache.put(key, new UnitOfWorkCacheItem<>(value));
                }
            }

            set(key, value, optionsFactory != null ? optionsFactory.get() : null, hideErrors, considerUow);
        }

        return value;
    }

    @Override
    public List<KeyValuePair<String, TCacheItem>> getOrAddMany(
            List<String> keys,
            Function<List<String>, List<KeyValuePair<String, TCacheItem>>> factory,
            Supplier<DistributedCacheEntryOptions> optionsFactory,
            Boolean hideErrors,
            Boolean considerUow) {

        List<KeyValuePair<String, TCacheItem>> result;

        if (!(cache instanceof ICacheSupportsMultipleItems)) {
            result = getManyFallback(
                    keys,
                    hideErrors,
                    considerUow
            );
        } else {
            List<String> notCachedKeys = new ArrayList<>();
            List<KeyValuePair<String, TCacheItem>> cachedValues = new ArrayList<>();

            if (shouldConsiderUow(considerUow)) {
                Map<String, UnitOfWorkCacheItem<TCacheItem>> uowCache = GetUnitOfWorkCache();
                for (String key : keys) {
                    UnitOfWorkCacheItem<TCacheItem> cacheItem = uowCache.getOrDefault(key, null);
                    if (cacheItem != null) {
                        TCacheItem value = cacheItem.getUnRemovedValueOrNull();
                        if (value != null) {
                            cachedValues.add(new KeyValuePair<>(key, value));
                        }
                    }

                }
                Set<String> cachedKeys = cachedValues.stream().map(KeyValuePair::getKey).collect(Collectors.toSet());
                notCachedKeys = keys.stream().filter(x -> !cachedKeys.contains(x)).collect(Collectors.toList());
                if (notCachedKeys.isEmpty()) {
                    return cachedValues;
                }
            }
            //--
            hideErrors = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
            byte[][] cachedBytes;

            List<String> readKeys = !notCachedKeys.isEmpty() ? notCachedKeys : keys;
            try {
                cachedBytes = ((ICacheSupportsMultipleItems) cache).getMany(readKeys.stream().map(this::normalizeKey).collect(Collectors.toList()));
            } catch (Exception ex) {
                if (hideErrors) {
                    handleException(ex);
                    return toCacheItemsWithDefaultValues(keys);
                }

                throw ex;
            }

            cachedValues.addAll(toCacheItems(cachedBytes, readKeys));
            result = new ArrayList<>(cachedValues);
        }
        if (result.stream().allMatch(x -> x.getValue() != null)) {
            return result;
        }

        List<String> missingKeys = new ArrayList<>();
        List<Integer> missingValuesIndex = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            if (result.get(i).getValue() != null) {
                continue;
            }

            missingKeys.add(keys.get(i));
            missingValuesIndex.add(i);
        }

        List<KeyValuePair<String, TCacheItem>> missingValues = factory.apply(missingKeys);
        Queue<KeyValuePair<String, TCacheItem>> valueQueue = new LinkedList<>(missingValues);

        setMany(missingValues, optionsFactory != null ? optionsFactory.get() : null, hideErrors, considerUow);

        for (Integer index : missingValuesIndex) {
            result.set(index, valueQueue.poll());
        }

        return result;
    }

    /**
     * Sets the cache item value for the provided key.
     *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="value">The cache item value to set in the cache.</param>
     * <param name="options">The cache options for the value.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     */
    @Override
    public void set(
            String key,
            TCacheItem value,
            DistributedCacheEntryOptions options,
            Boolean hideErrors,
            Boolean considerUow) {
        if (shouldConsiderUow(considerUow)) {
            Map<String, UnitOfWorkCacheItem<TCacheItem>> uowCache = GetUnitOfWorkCache();
            UnitOfWorkCacheItem<TCacheItem> cacheItem = uowCache.get(key);
            if (cacheItem != null) {
                cacheItem.setValue(value);
            } else {
                uowCache.put(key, new UnitOfWorkCacheItem<>(value));
            }
            // ReSharper disable once PossibleNullReferenceException
            unitOfWorkManager.getCurrent().onCompleted(() -> {
                boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
                try {
                    cache.put(normalizeKey(key), value);
                    //TODO 缓存配置，例如过期时间，ABP是在此处设置，spring cache是通过设置cacheManager可以指定，所以这个配置方式要修改
                } catch (Exception ex) {
                    if (hide) {
                        handleException(ex);
                        return;
                    }

                    throw ex;
                }
            });
        } else {
            boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
            try {
                cache.put(normalizeKey(key), value);
            } catch (Exception ex) {
                if (hide) {
                    handleException(ex);
                    return;
                }

                throw ex;
            }
        }
    }

    @Override
    public void setMany(
            List<KeyValuePair<String, TCacheItem>> items,
            DistributedCacheEntryOptions options,
            Boolean hideErrors,
            Boolean considerUow) {

        if (!(cache instanceof ICacheSupportsMultipleItems)) {
            setManyFallback(
                    items,
                    options,
                    hideErrors,
                    considerUow
            );

            return;
        }

        if (shouldConsiderUow(considerUow)) {
            Map<String, UnitOfWorkCacheItem<TCacheItem>> uowCache = GetUnitOfWorkCache();

            for (KeyValuePair<String, TCacheItem> pair : items) {
                UnitOfWorkCacheItem<TCacheItem> cacheItem = uowCache.get(pair.getKey());
                if (cacheItem != null) {
                    cacheItem.setValue(pair.getValue());
                } else {
                    uowCache.put(pair.getKey(), new UnitOfWorkCacheItem<>(pair.getValue()));
                }
            }

            // ReSharper disable once PossibleNullReferenceException
            unitOfWorkManager.getCurrent().onCompleted(() ->
            {
                boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();

                try {
                    ((ICacheSupportsMultipleItems) cache).setMany(toRawCacheItems(items), options);
                } catch (Exception ex) {
                    if (hide) {
                        handleException(ex);
                        return;
                    }

                    throw ex;
                }
            });
        } else {
            boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();

            try {
                ((ICacheSupportsMultipleItems) cache).setMany(toRawCacheItems(items), options);
            } catch (Exception ex) {
                if (hide) {
                    handleException(ex);
                    return;
                }

                throw ex;
            }
        }
    }

    protected void setManyFallback(
            List<KeyValuePair<String, TCacheItem>> items,
            DistributedCacheEntryOptions options,
            Boolean hideErrors,
            Boolean considerUow) {
        hideErrors = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();

        try {
            for (KeyValuePair<String, TCacheItem> item : items) {
                set(
                        item.getKey(),
                        item.getValue(),
                        options,
                        false,
                        considerUow
                );
            }
        } catch (Exception ex) {
            if (hideErrors) {
                handleException(ex);
                return;
            }

            throw ex;
        }
    }

    /**
     * Refreshes the cache value of the given key, and resets its sliding expiration timeout.
     *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     */
    @Override
    public void refresh(
            String key,
            Boolean hideErrors) {
        hideErrors = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();

        try {
            String normalizeKey = normalizeKey(key);
            TCacheItem value = cache.get(normalizeKey, cacheItemType);
            if (value != null) {
                cache.put(normalizeKey, value);
            }
        } catch (Exception ex) {
            if (hideErrors) {
                handleException(ex);
                return;
            }

            throw ex;
        }
    }

    @Override
    public void refreshMany(
            List<String> keys,
            Boolean hideErrors) {
        hideErrors = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();

        try {
            if (cache instanceof ICacheSupportsMultipleItems) {
                ((ICacheSupportsMultipleItems) cache).refreshMany(keys.stream().map(this::normalizeKey).collect(Collectors.toList()));
            } else {
                for (String key : keys) {
                    refresh(key, hideErrors);
                }
            }
        } catch (Exception ex) {
            if (hideErrors) {
                handleException(ex);
                return;
            }

            throw ex;
        }
    }

    /**
     * Removes the cache item for given key from cache.
     *
     * <param name="key">The key of cached item to be retrieved from the cache.</param>
     * <param name="considerUow">This will store the cache in the current unit of work until the end of the current unit of work does not really affect the cache.</param>
     * <param name="hideErrors">Indicates to throw or hide the exceptions for the distributed cache.</param>
     */
    @Override
    public void remove(
            String key,
            Boolean hideErrors,
            Boolean considerUow) {
        if (shouldConsiderUow(considerUow)) {
            Map<String, UnitOfWorkCacheItem<TCacheItem>> uowCache = GetUnitOfWorkCache();
            UnitOfWorkCacheItem<TCacheItem> cacheItem = uowCache.get(key);
            if (cacheItem != null) {
                uowCache.get(key).removeValue();
            }

            // ReSharper disable once PossibleNullReferenceException
            unitOfWorkManager.getCurrent().onCompleted(() ->
            {
                boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
                try {
                    cache.evict(normalizeKey(key));
                } catch (Exception ex) {
                    if (hide) {
                        handleException(ex);
                        return;
                    }
                    throw ex;
                }
            });
        } else {
            boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
            try {
                cache.evict(normalizeKey(key));
            } catch (Exception ex) {
                if (hide) {
                    handleException(ex);
                    return;
                }
                throw ex;
            }
        }
    }

    @Override
    public void removeMany(
            List<String> keys,
            Boolean hideErrors,
            Boolean considerUow) {
        if (cache instanceof ICacheSupportsMultipleItems) {
            if (shouldConsiderUow(considerUow)) {
                Map<String, UnitOfWorkCacheItem<TCacheItem>> uowCache = GetUnitOfWorkCache();

                for (String key : keys) {
                    UnitOfWorkCacheItem<TCacheItem> cacheItem = uowCache.get(key);
                    if (cacheItem != null) {
                        uowCache.get(key).removeValue();
                    }
                }
                // ReSharper disable once PossibleNullReferenceException
                unitOfWorkManager.getCurrent().onCompleted(() ->
                {
                    boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
                    try {
                        ((ICacheSupportsMultipleItems) cache).removeMany(
                                keys.stream().map(this::normalizeKey).collect(Collectors.toList())
                        );
                    } catch (Exception ex) {
                        if (hide) {
                            handleException(ex);
                            return;
                        }
                        throw ex;
                    }
                });
            } else {
                boolean hide = hideErrors != null ? hideErrors : distributedCacheOption.isHideErrors();
                try {
                    ((ICacheSupportsMultipleItems) cache).removeMany(
                            keys.stream().map(this::normalizeKey).collect(Collectors.toList())
                    );
                } catch (Exception ex) {
                    if (hide) {
                        handleException(ex);
                        return;
                    }
                    throw ex;
                }
            }
        } else {
            for (String key : keys) {
                remove(key, hideErrors, considerUow);
            }
        }
    }

    protected void handleException(Exception ex) {

        log.warn("DistributedCache HandleException", ex);
        exceptionNotifier.notify(new ExceptionNotificationContext(ex, Level.WARN, true));
    }

    protected List<KeyValuePair<String, TCacheItem>> toCacheItems(byte[][] itemBytes, List<String> itemKeys) {
        if (itemBytes.length != itemKeys.size()) {
            throw new SystemException("count of the item bytes should be same with the count of the given keys");
        }

        List<KeyValuePair<String, TCacheItem>> result = new ArrayList<>();

        for (int i = 0; i < itemKeys.size(); i++) {
            result.add(
                    new KeyValuePair<>(
                            itemKeys.get(i),
                            toCacheItem(itemBytes[i])
                    )
            );
        }

        return result;
    }

    @Nullable
    protected TCacheItem toCacheItem(@Nullable byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        return serializer.deserialize(cacheItemType, bytes);
    }

    protected List<KeyValuePair<String, byte[]>> toRawCacheItems(List<KeyValuePair<String, TCacheItem>> items) {
        return items.stream().map(i -> new KeyValuePair<>(
                normalizeKey(i.getKey()),
                serializer.serialize(i.getValue())
        )).collect(Collectors.toList());
    }

    private List<KeyValuePair<String, TCacheItem>> toCacheItemsWithDefaultValues(List<String> keys) {
        return keys.stream().map(key -> new KeyValuePair<String, TCacheItem>(key, null)).collect(Collectors.toList());
    }

    protected boolean shouldConsiderUow(Boolean considerUow) {
        return considerUow && unitOfWorkManager.getCurrent() != null;
    }

    protected String getUnitOfWorkCacheKey() {
        return UowCacheName + cacheName;
    }

    protected Map<String, UnitOfWorkCacheItem<TCacheItem>> GetUnitOfWorkCache() {
        if (unitOfWorkManager.getCurrent() == null) {
            throw new SystemException("There is no active UOW.");
        }
        return UnitOfWorkExtensions.getOrAddItem(
                unitOfWorkManager.getCurrent(),
                getUnitOfWorkCacheKey(),
                key -> new HashMap<>()
        );
    }
}
