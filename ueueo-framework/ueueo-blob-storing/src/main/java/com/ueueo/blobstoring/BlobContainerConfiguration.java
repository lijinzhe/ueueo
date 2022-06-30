package com.ueueo.blobstoring;

import com.ueueo.exception.SystemException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlobContainerConfiguration {
    /**
     * The provider to be used to store BLOBs of this container.
     */
    private Class<?> providerType;

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
    private boolean isMultiTenant = true;

    private List<? extends IBlobNamingNormalizer> namingNormalizers;

    @NonNull
    private Map<String, Object> properties;

    @Nullable
    private BlobContainerConfiguration fallbackConfiguration;

    public BlobContainerConfiguration(BlobContainerConfiguration fallbackConfiguration) {
        this.namingNormalizers = new ArrayList<>();
        this.fallbackConfiguration = fallbackConfiguration;
        this.properties = new HashMap<>();
    }

    @Nullable
    public <T> T getConfigurationOrDefault(String name, T defaultValue) {
        return (T) getConfigurationOrNull(name, defaultValue);
    }

    @Nullable
    public Object getConfigurationOrNull(String name, Object defaultValue) {
        Object obj = properties.get(name);
        if (obj != null) {
            return obj;
        }
        if (fallbackConfiguration != null) {
            return fallbackConfiguration.getConfigurationOrNull(name, defaultValue);
        }
        return defaultValue;
    }

    public <T> T getConfiguration(@NonNull String name, Class<T> type) {
        return (T) getConfiguration(name);
    }

    public Object getConfiguration(@NonNull String name) {
        Object value = getConfigurationOrNull(name, null);
        if (value == null) {
            throw new SystemException(String.format("Could not find the configuration value for '%s'!", name));
        }

        return value;
    }

    @NonNull
    public BlobContainerConfiguration setConfiguration(@NonNull String name, @Nullable Object value) {
        Assert.hasLength(name, "name must not empty!");
        Assert.notNull(value, "value must not null!");

        properties.put(name, value);

        return this;
    }

    @NonNull
    public BlobContainerConfiguration clearConfiguration(@NonNull String name) {
        Assert.hasLength(name, "name must not empty!");

        properties.remove(name);

        return this;
    }

    public Class<?> getProviderType() {
        return providerType;
    }

    public void setProviderType(Class<?> providerType) {
        this.providerType = providerType;
    }

    public boolean isMultiTenant() {
        return isMultiTenant;
    }

    public void setMultiTenant(boolean multiTenant) {
        isMultiTenant = multiTenant;
    }

    public List<? extends IBlobNamingNormalizer> getNamingNormalizers() {
        return namingNormalizers;
    }
}
