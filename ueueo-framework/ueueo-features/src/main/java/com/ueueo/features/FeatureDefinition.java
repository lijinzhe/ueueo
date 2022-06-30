package com.ueueo.features;

import com.ueueo.SystemException;
import com.ueueo.localization.FixedLocalizableString;
import com.ueueo.localization.ILocalizableString;
import com.ueueo.validation.stringvalues.IStringValueType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-17 16:53
 */
@Data
public class FeatureDefinition {

    /** Unique name of the feature. */
    @NonNull
    @Setter(AccessLevel.PRIVATE)
    private String name;

    private ILocalizableString displayName;

    private ILocalizableString description;

    /**
     * Parent of this feature, if one exists.
     * If set, this feature can be enabled only if the parent is enabled.
     */
    @Setter(AccessLevel.PRIVATE)
    private FeatureDefinition parent;

    /** List of child features. */
    private List<FeatureDefinition> children;

    /** Default value of the feature. */
    private String defaultValue;

    /** Can clients see this feature and it's value. Default: true. */
    private boolean isVisibleToClients = true;

    /** Can host use this feature. Default: true. */
    private boolean isAvailableToHost = true;
    /**
     * A list of allowed providers to get/set value of this feature.
     * An empty list indicates that all providers are allowed.
     */
    @Setter(AccessLevel.PRIVATE)
    private List<String> allowedProviders;

    /**
     * Input type.
     * This can be used to prepare an input for changing this feature's value.
     * Default: <see cref="ToggleStringValueType"/>.
     */
    @Nullable
    private IStringValueType valueType;

    /** Can be used to get/set custom properties for this feature. */
    @Setter(AccessLevel.PRIVATE)
    private Map<String, Object> properties;

    /**
     * Gets a key-value on the <see cref="Properties"/>.
     *
     * @param name Name of the property
     *
     * @return Returns the value in the <see cref="Properties"/> dictionary by given <paramref name="name"/>.
     * Returns null if given <paramref name="name"/> is not present in the <see cref="Properties"/> dictionary.
     */
    public Object getProperty(String name) {
        return properties.get(name);
    }

    /**
     * Sets a key-value on the <see cref="Properties"/>.
     *
     * @param name  Name of the property
     * @param value the value in the <see cref="Properties"/> dictionary
     */
    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    public FeatureDefinition(@NonNull String name,
                             String defaultValue,
                             ILocalizableString displayName,
                             ILocalizableString description,
                             IStringValueType valueType,
                             Boolean isVisibleToClients,
                             Boolean isAvailableToHost) {
        this.name = name;
        if (displayName == null) {
            this.displayName = new FixedLocalizableString(name);
        } else {
            this.displayName = displayName;
        }
        this.description = description;
        this.children = new ArrayList<>();
        this.defaultValue = defaultValue;
        this.isVisibleToClients = isVisibleToClients != null ? isVisibleToClients : true;
        this.isAvailableToHost = isAvailableToHost != null ? isAvailableToHost : true;
        this.allowedProviders = new ArrayList<>();
        this.valueType = valueType;
        this.properties = new HashMap<>();
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
    public FeatureDefinition withProperty(String key, Object value) {
        this.properties.put(key, value);
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
    public FeatureDefinition withProviders(List<String> providers) {
        if (CollectionUtils.isNotEmpty(providers)) {
            this.allowedProviders.addAll(providers);
        }
        return this;
    }

    /**
     * Adds a child feature.
     *
     * @param name
     * @param defaultValue
     * @param displayName
     * @param description
     * @param valueType
     * @param isVisibleToClients
     * @param isAvailableToHost
     *
     * @return Returns a newly created child feature
     */
    public FeatureDefinition createChild(String name,
                                         String defaultValue,
                                         ILocalizableString displayName,
                                         ILocalizableString description,
                                         IStringValueType valueType,
                                         Boolean isVisibleToClients,
                                         Boolean isAvailableToHost) {
        FeatureDefinition feature = new FeatureDefinition(name, defaultValue, displayName, description, valueType, isVisibleToClients, isAvailableToHost);
        feature.parent = this;
        this.children.add(feature);
        return feature;
    }

    public void removeChild(String name) {
        FeatureDefinition featureToRemove = children.stream().filter(f -> StringUtils.equals(f.getName(), name)).findFirst().orElse(null);
        if (featureToRemove == null) {
            throw new SystemException(String.format("Could not find a feature named '{%s}' in the Children of this feature '{%s}'.", name, getName()));
        }
        featureToRemove.parent = null;
        children.remove(featureToRemove);
    }

    @Override
    public String toString() {
        return String.format("[FeatureDefinition]: %s", getName());
    }
}
