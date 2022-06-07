package com.ueueo.localization;

import com.ueueo.AbpException;
import org.springframework.lang.Nullable;

import java.util.HashMap;

public class LocalizationResourceDictionary extends HashMap<Class<?>, LocalizationResource> {

    public LocalizationResource put(Class<?> resourceType, @Nullable String defaultCultureName) {
        if (containsKey(resourceType)) {
            throw new AbpException("This resource is already added before: " + resourceType.getName());
        }

        return put(resourceType, new LocalizationResource(resourceType, defaultCultureName, null));
    }
}
