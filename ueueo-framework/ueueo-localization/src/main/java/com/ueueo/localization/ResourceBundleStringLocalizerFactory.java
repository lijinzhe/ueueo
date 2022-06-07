package com.ueueo.localization;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ResourceBundleStringLocalizerFactory implements IStringLocalizerFactory {

    private final ConcurrentHashMap<Class<?>, IStringLocalizer> localizerCache = new ConcurrentHashMap<>();

    @Override
    public IStringLocalizer create(Class<?> resourceType) {

        if (ResourceBundleStringLocalizer.class.isAssignableFrom(resourceType)) {
            IStringLocalizer cachedLocalizer = localizerCache.get(resourceType);
            if (cachedLocalizer != null) {
                return cachedLocalizer;
            }

            synchronized (localizerCache) {
                return localizerCache.computeIfAbsent(resourceType, type -> {
                    try {
                        return (IStringLocalizer) type.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });
            }
        }
        return null;
    }

    @Override
    public IStringLocalizer create(String... basenames) {
        return new ResourceBundleStringLocalizer(basenames);
    }
}
