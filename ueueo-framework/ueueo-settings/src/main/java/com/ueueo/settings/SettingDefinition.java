package com.ueueo.settings;

import com.ueueo.localization.ILocalizableString;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.*;

/**
 * @author Lee
 * @date 2021-08-18 20:18
 */
@Data
public class SettingDefinition {

    /** Unique name of the setting. */
    @Setter(AccessLevel.PRIVATE)
    private String name;

    private ILocalizableString displayName;

    private ILocalizableString description;

    /** Default value of the setting. */
    private String defaultValue;

    /**
     * Can clients see this setting and it's value.
     * It maybe dangerous for some settings to be visible to clients (such as an email server password).
     * Default: false.
     */
    private boolean isVisibleToClients = false;

    /**
     * A list of allowed providers to get/set value of this setting.
     * An empty list indicates that all providers are allowed.
     */
    @Setter(AccessLevel.PRIVATE)
    private List<String> providers;

    /**
     * Is this setting inherited from parent scopes.
     * Default: True.
     */
    private boolean isInherited = true;

    /**
     * Can be used to get/set custom properties for this setting definition.
     */
    @Setter(AccessLevel.PRIVATE)
    private Map<String, Object> properties;

    /**
     * Is this setting stored as encrypted in the data source.
     * Default: False.
     */
    private boolean isEncrypted = false;

    public SettingDefinition(
            String name,
            String defaultValue,
            ILocalizableString displayName,
            ILocalizableString description,
            boolean isVisibleToClients,
            boolean isInherited,
            boolean isEncrypted) {
        Objects.requireNonNull(name);
        this.name = name;
        this.defaultValue = defaultValue;
        this.displayName = displayName;
        this.description = description;
        this.isVisibleToClients = isVisibleToClients;
        this.isInherited = isInherited;
        this.isEncrypted = isEncrypted;

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

}
