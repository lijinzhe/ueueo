using System;
using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.Localization.VirtualFiles.Json;

namespace Volo.Abp.Localization;

public static class LocalizationResourceExtensions
{
    public static LocalizationResource AddVirtualJson(
        @NonNull this LocalizationResource localizationResource,
        @NonNull String virtualPath)
    {
        Objects.requireNonNull(localizationResource, nameof(localizationResource));
        Objects.requireNonNull(virtualPath, nameof(virtualPath));

        localizationResource.Contributors.Add(new JsonVirtualFileLocalizationResourceContributor(
            virtualPath.EnsureStartsWith('/')
        ));

        return localizationResource;
    }

    public static LocalizationResource AddBaseTypes(
        @NonNull this LocalizationResource localizationResource,
        @NonNull params Type[] types)
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
