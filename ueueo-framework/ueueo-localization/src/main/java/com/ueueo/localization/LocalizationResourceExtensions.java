package com.ueueo.localization;

import com.ueueo.localization.virtualfiles.json.JsonVirtualFileLocalizationResourceContributor;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class LocalizationResourceExtensions
{
    public static LocalizationResource addVirtualJson(
            @NonNull  LocalizationResource localizationResource,
            @NonNull String virtualPath)
    {
        Objects.requireNonNull(localizationResource);
        Objects.requireNonNull(virtualPath);

        localizationResource.getContributors().add(new JsonVirtualFileLocalizationResourceContributor(virtualPath));

        return localizationResource;
    }

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
