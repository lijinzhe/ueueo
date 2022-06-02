package com.ueueo.localization;

import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
public class AbpStringLocalizerFactory implements IStringLocalizerFactory, IAbpStringLocalizerFactoryWithDefaultResourceSupport {
    protected AbpLocalizationOptions abpLocalizationOptions;
    protected ResourceManagerStringLocalizerFactory innerFactory;
    protected BeanFactory beanFactory;
    protected final ConcurrentHashMap<Class<?>, StringLocalizerCacheItem> localizerCache;

    //TODO: It's better to use decorator pattern for IStringLocalizerFactory instead of getting ResourceManagerStringLocalizerFactory as a dependency.
    public AbpStringLocalizerFactory(
            ResourceManagerStringLocalizerFactory innerFactory,
            AbpLocalizationOptions abpLocalizationOptions,
            BeanFactory beanFactory) {
        this.innerFactory = innerFactory;
        this.beanFactory = beanFactory;
        this.abpLocalizationOptions = abpLocalizationOptions;

        this.localizerCache = new ConcurrentHashMap<>();
    }

    @Override
    public IStringLocalizer create(Class<?> resourceType) {
        LocalizationResource resource = abpLocalizationOptions.getResources().get(resourceType);
        if (resource == null) {
            return innerFactory.create(resourceType);
        }
        StringLocalizerCacheItem cacheItem = localizerCache.get(resourceType);
        if (cacheItem != null) {
            return cacheItem.localizer;
        }

        synchronized (localizerCache) {
            return localizerCache.computeIfAbsent(resourceType, type -> createStringLocalizerCacheItem(resource)).localizer;
        }
    }

    private StringLocalizerCacheItem createStringLocalizerCacheItem(LocalizationResource resource) {
        for (Class<? extends ILocalizationResourceContributor> globalContributor : abpLocalizationOptions.getGlobalContributors()) {
            try {
                resource.getContributors().add(globalContributor.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LocalizationResourceInitializationContext context = new LocalizationResourceInitializationContext(resource, beanFactory);

        for (ILocalizationResourceContributor contributor : resource.getContributors()) {
            contributor.initialize(context);
        }

        return new StringLocalizerCacheItem(
                new AbpDictionaryBasedStringLocalizer(
                        resource,
                        resource.getBaseResourceTypes().stream().map(this::create).collect(Collectors.toList()),
                        abpLocalizationOptions
                )
        );
    }

    @Override
    public IStringLocalizer create(String baseName, String location) {
        //TODO: Investigate when this is called?

        return innerFactory.create(baseName, location);
    }

    @Getter
    protected class StringLocalizerCacheItem {
        private AbpDictionaryBasedStringLocalizer localizer;

        public StringLocalizerCacheItem(AbpDictionaryBasedStringLocalizer localizer) {
            this.localizer = localizer;
        }
    }

    @Override
    public IStringLocalizer createDefaultOrNull() {
        if (abpLocalizationOptions.getDefaultResourceType() == null) {
            return null;
        }

        return create(abpLocalizationOptions.getDefaultResourceType());
    }
}
