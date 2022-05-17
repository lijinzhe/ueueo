package com.ueueo.features;

import com.ueueo.localization.FixedLocalizableString;
import com.ueueo.localization.ILocalizableString;
import com.ueueo.validation.IStringValueType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public class FeatureGroupDefinition {
    /** Unique name of the group. */
    @Getter
    private String name;

    private final Map<String, Object> properties;

    @Getter
    @Setter
    private ILocalizableString displayName;

    private final List<FeatureDefinition> features;

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
     * @param value
     */
    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    protected FeatureGroupDefinition(String name, ILocalizableString displayName) {
        this.name = name;
        if (displayName != null) {
            this.displayName = displayName;
        } else {
            this.displayName = new FixedLocalizableString(name);
        }
        this.properties = new HashMap<>();
        this.features = new ArrayList<>();
    }

    public FeatureDefinition addFeature(String name,
                                        String defaultValue,
                                        ILocalizableString displayName,
                                        ILocalizableString description,
                                        IStringValueType valueType,
                                        Boolean isVisibleToClients) {
        FeatureDefinition feature = new FeatureDefinition(name, defaultValue, displayName,
                description, valueType, isVisibleToClients, null);
        this.features.add(feature);
        return feature;
    }

    public List<FeatureDefinition> getFeaturesWithChildren() {
        List<FeatureDefinition> features = new ArrayList<>();

        for (FeatureDefinition feature : this.features) {
            addFeatureToListRecursively(features, feature);
        }

        return features;
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
    public FeatureGroupDefinition withProperty(String key, Object value) {
        setProperty(key, value);
        return this;
    }

    private void addFeatureToListRecursively(List<FeatureDefinition> features, FeatureDefinition feature) {
        features.add(feature);
        for (FeatureDefinition child : feature.getChildren()) {
            addFeatureToListRecursively(features, child);
        }
    }

    @Override
    public String toString() {
        return String.format("[FeatureGroupDefinition %s]", getName());
    }
}
