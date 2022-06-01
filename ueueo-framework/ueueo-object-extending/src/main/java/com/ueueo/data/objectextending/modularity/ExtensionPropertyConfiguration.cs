using System;
using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.Localization;
using Volo.Abp.Reflection;

namespace Volo.Abp.ObjectExtending.Modularity;

public class ExtensionPropertyConfiguration : IHasNameWithLocalizableDisplayName, IBasicObjectExtensionPropertyInfo
{
    [NotNull]
    public EntityExtensionConfiguration EntityExtensionConfiguration;//  { get; }

    [NotNull]
    public String Name;//  { get; }

    [NotNull]
    public Type Type;//  { get; }

    [NotNull]
    public List<Attribute> Attributes;//  { get; }

    [NotNull]
    public List<Action<ObjectExtensionPropertyValidationContext>> Validators;//  { get; }

    [CanBeNull]
    public ILocalizableString DisplayName;// { get; set; }

    [NotNull]
    public Dictionary<String, Object> Configuration;//  { get; }

    /**
     * Single point to enable/disable this property for the clients (UI and API).
     * If this is false, the configuration made in the <see cref="UI"/> and the <see cref="Api"/>
     * properties are not used.
     * Default: true.
    */
    public boolean IsAvailableToClients;// { get; set; } = true;

    [NotNull]
    public ExtensionPropertyEntityConfiguration Entity;//  { get; }

    [NotNull]
    public ExtensionPropertyUiConfiguration UI;//  { get; }

    [NotNull]
    public ExtensionPropertyApiConfiguration Api;//  { get; }

    /**
     * Uses as the default value if <see cref="DefaultValueFactory"/> was not set.
    */
    [CanBeNull]
    public Object DefaultValue;// { get; set; }

    /**
     * Used with the first priority to create the default value for the property.
     * Uses to the <see cref="DefaultValue"/> if this was not set.
    */
    [CanBeNull]
    public Func<Object> DefaultValueFactory;// { get; set; }

    public ExtensionPropertyConfiguration(
        @NonNull EntityExtensionConfiguration entityExtensionConfiguration,
        @NonNull Type type,
        @NonNull String name)
    {
        EntityExtensionConfiguration = Objects.requireNonNull(entityExtensionConfiguration, nameof(entityExtensionConfiguration));
        Type = Objects.requireNonNull(type, nameof(type));
        Name = Objects.requireNonNull(name, nameof(name));

        Configuration = new Dictionary<String, Object>();
        Attributes = new List<Attribute>();
        Validators = new List<Action<ObjectExtensionPropertyValidationContext>>();

        Entity = new ExtensionPropertyEntityConfiguration();
        UI = new ExtensionPropertyUiConfiguration();
        Api = new ExtensionPropertyApiConfiguration();

        Attributes.AddRange(ExtensionPropertyHelper.GetDefaultAttributes(Type));
        DefaultValue = TypeHelper.GetDefaultValue(Type);
    }

    public Object GetDefaultValue()
    {
        return ExtensionPropertyHelper.GetDefaultValue(Type, DefaultValueFactory, DefaultValue);
    }
}
