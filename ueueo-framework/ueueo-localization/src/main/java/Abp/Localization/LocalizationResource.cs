using System;
using System.Collections.Generic;
using System.Linq;
using JetBrains.Annotations;

namespace Volo.Abp.Localization;

public class LocalizationResource
{
    [NotNull]
    public Type ResourceType;//  { get; }

    [NotNull]
    public String ResourceName => LocalizationResourceNameAttribute.GetName(ResourceType);

    [CanBeNull]
    public String DefaultCultureName;// { get; set; }

    [NotNull]
    public LocalizationResourceContributorList Contributors;//  { get; }

    [NotNull]
    public List<Type> BaseResourceTypes;//  { get; }

    public LocalizationResource(
        @Nonnull Type resourceType,
        @Nullable String defaultCultureName = null,
        @Nullable ILocalizationResourceContributor initialContributor = null)
    {
        ResourceType = Objects.requireNonNull(resourceType, nameof(resourceType));
        DefaultCultureName = defaultCultureName;

        BaseResourceTypes = new List<Type>();
        Contributors = new LocalizationResourceContributorList();

        if (initialContributor != null)
        {
            Contributors.Add(initialContributor);
        }

        AddBaseResourceTypes();
    }

    protected   void AddBaseResourceTypes()
    {
        var descriptors = ResourceType
            .GetCustomAttributes(true)
            .OfType<IInheritedResourceTypesProvider>();

        for (var descriptor in descriptors)
        {
            for (var baseResourceType in descriptor.GetInheritedResourceTypes())
            {
                BaseResourceTypes.AddIfNotContains(baseResourceType);
            }
        }
    }
}
