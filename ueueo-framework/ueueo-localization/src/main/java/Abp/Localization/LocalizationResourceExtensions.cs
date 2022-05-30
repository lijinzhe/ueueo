using System;
using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.Localization.VirtualFiles.Json;

namespace Volo.Abp.Localization;

public static class LocalizationResourceExtensions
{
    public static LocalizationResource AddVirtualJson(
        @Nonnull this LocalizationResource localizationResource,
        @Nonnull String virtualPath)
    {
        Objects.requireNonNull(localizationResource, nameof(localizationResource));
        Objects.requireNonNull(virtualPath, nameof(virtualPath));

        localizationResource.Contributors.Add(new JsonVirtualFileLocalizationResourceContributor(
            virtualPath.EnsureStartsWith('/')
        ));

        return localizationResource;
    }

    public static LocalizationResource AddBaseTypes(
        @Nonnull this LocalizationResource localizationResource,
        @Nonnull params Type[] types)
    {
        Objects.requireNonNull(localizationResource, nameof(localizationResource));
        Objects.requireNonNull(types, nameof(types));

        for (var type in types)
        {
            localizationResource.BaseResourceTypes.AddIfNotContains(type);
        }

        return localizationResource;
    }
}
