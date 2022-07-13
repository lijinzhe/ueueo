package com.ueueo.localization;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class LocalizationResourceExtensions
{

    public static LocalizationResource addBaseTypes(
        @NonNull  LocalizationResource localizationResource,
        @NonNull  Class<?>... types)
    {
        Objects.requireNonNull(localizationResource);
        Objects.requireNonNull(types);

        for (Class<?> type : types)
        {
            localizationResource.getBaseResourceTypes().add(type);
        }
        return localizationResource;
    }
}
