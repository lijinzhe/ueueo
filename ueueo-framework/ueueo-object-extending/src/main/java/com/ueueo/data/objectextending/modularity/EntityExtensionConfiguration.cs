using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using JetBrains.Annotations;
using Volo.Abp.Localization;

namespace Volo.Abp.ObjectExtending.Modularity;

public class EntityExtensionConfiguration
{
    [NotNull]
    protected ExtensionPropertyConfigurationDictionary Properties;//  { get; }

    [NotNull]
    public List<Action<ObjectExtensionValidationContext>> Validators;//  { get; }

    public Dictionary<String, Object> Configuration;//  { get; }

    public EntityExtensionConfiguration()
    {
        Properties = new ExtensionPropertyConfigurationDictionary();
        Validators = new List<Action<ObjectExtensionValidationContext>>();
        Configuration = new Dictionary<String, Object>();
    }

    [NotNull]
    public   EntityExtensionConfiguration AddOrUpdateProperty<TProperty>(
        @Nonnull String propertyName,
        @Nullable Action<ExtensionPropertyConfiguration> configureAction = null)
    {
        return AddOrUpdateProperty(
            typeof(TProperty),
            propertyName,
            configureAction
        );
    }

    [NotNull]
    public   EntityExtensionConfiguration AddOrUpdateProperty(
        @Nonnull Type propertyType,
        @Nonnull String propertyName,
        @Nullable Action<ExtensionPropertyConfiguration> configureAction = null)
    {
        Objects.requireNonNull(propertyType, nameof(propertyType));
        Objects.requireNonNull(propertyName, nameof(propertyName));

        var propertyInfo = Properties.GetOrAdd(
            propertyName,
            () => new ExtensionPropertyConfiguration(this, propertyType, propertyName)
        );
        configureAction?.Invoke(propertyInfo);

        NormalizeProperty(propertyInfo);

        if (!propertyInfo.UI.Lookup.Url.IsNullOrEmpty())
        {
            AddLookupTextProperty(propertyInfo);
            propertyInfo.UI.OnTable.IsVisible = false;
        }
        return this;
    }

    private void AddLookupTextProperty(ExtensionPropertyConfiguration propertyInfo)
    {
        var lookupTextPropertyName = $"{propertyInfo.Name}_Text";
        var lookupTextPropertyInfo = Properties.GetOrAdd(
           lookupTextPropertyName,
           () => new ExtensionPropertyConfiguration(this, typeof(String), lookupTextPropertyName)
       );

        lookupTextPropertyInfo.DisplayName = propertyInfo.DisplayName;
    }

    [NotNull]
    public   ImmutableList<ExtensionPropertyConfiguration> GetProperties()
    {
        return Properties.Values.ToImmutableList();
    }

    private static void NormalizeProperty(ExtensionPropertyConfiguration propertyInfo)
    {
        if (!propertyInfo.Api.OnGet.IsAvailable)
        {
            propertyInfo.UI.OnTable.IsVisible = false;
        }

        if (!propertyInfo.Api.OnCreate.IsAvailable)
        {
            propertyInfo.UI.OnCreateForm.IsVisible = false;
        }

        if (!propertyInfo.Api.OnUpdate.IsAvailable)
        {
            propertyInfo.UI.OnEditForm.IsVisible = false;
        }
    }
}
