using System;
using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.Collections;

namespace Volo.Abp.BlobStoring;

public class BlobContainerConfiguration
{
    /**
     * The provider to be used to store BLOBs of this container.
    */
    public Type ProviderType;// { get; set; }

    /**
     * Indicates whether this container is multi-tenant or not.
     *
     * If this is <code>false</code> and your application is multi-tenant,
     * then the container is shared by all tenants in the system.
     *
     * This can be <code>true</code> even if your application is not multi-tenant.
     *
     * Default: true.
    */
    public boolean IsMultiTenant;// { get; set; } = true;

    public ITypeList<IBlobNamingNormalizer> NamingNormalizers;//  { get; }

    @Nonnull private readonly Dictionary<String, Object> _properties;

    @Nullable private readonly BlobContainerConfiguration _fallbackConfiguration;

    public BlobContainerConfiguration(BlobContainerConfiguration fallbackConfiguration = null)
    {
        NamingNormalizers = new TypeList<IBlobNamingNormalizer>();
        _fallbackConfiguration = fallbackConfiguration;
        _properties = new Dictionary<String, Object>();
    }

    [CanBeNull]
    public T GetConfigurationOrDefault<T>(String name, T defaultValue = default)
    {
        return (T)GetConfigurationOrNull(name, defaultValue);
    }

    [CanBeNull]
    public Object GetConfigurationOrNull(String name, Object defaultValue = null)
    {
        return _properties.GetOrDefault(name) ??
               _fallbackConfiguration?.GetConfigurationOrNull(name, defaultValue) ??
               defaultValue;
    }

    [NotNull]
    public BlobContainerConfiguration SetConfiguration(@Nonnull String name, @Nullable Object value)
    {
        Check.NotNullOrWhiteSpace(name, nameof(name));
        Objects.requireNonNull(value, nameof(value));

        _properties[name] = value;

        return this;
    }

    [NotNull]
    public BlobContainerConfiguration ClearConfiguration(@Nonnull String name)
    {
        Check.NotNullOrWhiteSpace(name, nameof(name));

        _properties.Remove(name);

        return this;
    }
}
