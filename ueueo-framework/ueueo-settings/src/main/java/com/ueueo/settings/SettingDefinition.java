package com.ueueo.settings;

import java.util.*;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 20:18
 */
public class SettingDefinition {

    /** Unique name of the setting. */
    private String name;

    private String displayName;

    private String description;

    /** Default value of the setting. */
    private String defaultValue;

    /**
     * Can clients see this setting and it's value.
     * It maybe dangerous for some settings to be visible to clients (such as an email server password).
     * Default: false.
     */
    private Boolean isVisibleToClients;

    /**
     * A list of allowed providers to get/set value of this setting.
     * An empty list indicates that all providers are allowed.
     */
    private Collection<String> providers;

    /**
     * Is this setting inherited from parent scopes.
     * Default: True.
     */
    private Boolean isInherited;

    /**
     * Can be used to get/set custom properties for this setting definition.
     */
    private Map<String, Object> properties;

    /**
     * Is this setting stored as encrypted in the data source.
     * Default: False.
     */
    private Boolean isEncrypted;

    public SettingDefinition(
            String name,
            String defaultValue,
            String displayName,
            String description,
            Boolean isVisibleToClients,
            Boolean isInherited,
            Boolean isEncrypted) {
        Objects.requireNonNull(name);
        this.name = name;
        this.defaultValue = defaultValue;
        this.displayName = displayName;
        this.description = description;
        this.isVisibleToClients = isVisibleToClients != null ? isVisibleToClients : false;
        this.isInherited = isInherited != null ? isInherited : true;
        this.isEncrypted = isEncrypted != null ? isEncrypted : false;

        properties = new HashMap<>();
        providers = new ArrayList<>();
    }

    /**
     * Sets a property in the <see cref="Properties"/> dictionary.
     * This is a shortcut for nested calls on this object.
     *
     * @param key
     * @param value
     *
     * @return
     */
    public SettingDefinition withProperty(String key, String value) {
        properties.put(key, value);
        return this;
    }

    /**
     * Sets a property in the <see cref="Properties"/> dictionary.
     * This is a shortcut for nested calls on this object.
     *
     * @param providers
     *
     * @return
     */
    public SettingDefinition withProviders(String... providers) {
        if (providers != null && providers.length > 0) {
            this.providers.addAll(Arrays.asList(providers));
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getIsVisibleToClients() {
        return isVisibleToClients;
    }

    public void setIsVisibleToClients(Boolean visibleToClients) {
        isVisibleToClients = visibleToClients;
    }

    public Collection<String> getProviders() {
        return providers;
    }

    public Boolean getIsInherited() {
        return isInherited;
    }

    public void setIsInherited(Boolean inherited) {
        isInherited = inherited;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Boolean getIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(Boolean encrypted) {
        isEncrypted = encrypted;
    }
}
