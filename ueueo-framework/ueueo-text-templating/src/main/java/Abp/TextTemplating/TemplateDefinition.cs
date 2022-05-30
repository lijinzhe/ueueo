using System;
using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.Localization;

namespace Volo.Abp.TextTemplating;

public class TemplateDefinition : IHasNameWithLocalizableDisplayName
{
    public const int MaxNameLength = 128;

    [NotNull]
    public String Name;//  { get; }

    [NotNull]
    public ILocalizableString DisplayName {
        get => _displayName;
        set {
            Objects.requireNonNull(value, nameof(value));
            _displayName = value;
        }
    }
    private ILocalizableString _displayName;

    public boolean IsLayout;//  { get; }

    [CanBeNull]
    public String Layout;// { get; set; }

    [CanBeNull]
    public Type LocalizationResource;// { get; set; }

    public boolean IsInlineLocalized;// { get; set; }

    [CanBeNull]
    public String DefaultCultureName;//  { get; }

    [CanBeNull]
    public String RenderEngine;// { get; set; }

    /**
     * Gets/sets a key-value on the <see cref="Properties"/>.
    *
     * <param name="name">Name of the property</param>
     * <returns>
     * Returns the value in the <see cref="Properties"/> dictionary by given <see cref="name"/>.
     * Returns null if given <see cref="name"/> is not present in the <see cref="Properties"/> dictionary.
     * </returns>
     */
    [CanBeNull]
    public Object this[String name] {
        get => Properties.GetOrDefault(name);
        set => Properties[name] = value;
    }

    /**
     * Can be used to get/set custom properties for this feature.
    */
    [NotNull]
    public Dictionary<String, Object> Properties;//  { get; }

    public TemplateDefinition(
        @Nonnull String name,
        @Nullable Type localizationResource = null,
        @Nullable ILocalizableString displayName = null,
        boolean isLayout = false,
        String layout = null,
        String defaultCultureName = null)
    {
        Name = Check.NotNullOrWhiteSpace(name, nameof(name), MaxNameLength);
        LocalizationResource = localizationResource;
        DisplayName = displayName ?? new FixedLocalizableString(Name);
        IsLayout = isLayout;
        Layout = layout;
        DefaultCultureName = defaultCultureName;
        Properties = new Dictionary<String, Object>();
    }

    /**
     * Sets a property in the <see cref="Properties"/> dictionary.
     * This is a shortcut for nested calls on this object.
    */
    public   TemplateDefinition WithProperty(String key, Object value)
    {
        Properties[key] = value;
        return this;
    }

    /**
     * Sets the Render Engine of <see cref="TemplateDefinition"/>.
    */
    public   TemplateDefinition WithRenderEngine(String renderEngine)
    {
        RenderEngine = renderEngine;
        return this;
    }
}
