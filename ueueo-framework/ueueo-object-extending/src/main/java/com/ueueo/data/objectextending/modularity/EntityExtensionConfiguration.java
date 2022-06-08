package com.ueueo.data.objectextending.modularity;

import com.ueueo.data.objectextending.ObjectExtensionValidationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class EntityExtensionConfiguration {
    @NonNull
    protected Map<String, ExtensionPropertyConfiguration> properties;

    @NonNull
    private List<Consumer<ObjectExtensionValidationContext>> validators;

    private Map<String, Object> configuration;

    public EntityExtensionConfiguration() {
        properties = new HashMap<>();
        validators = new ArrayList<>();
        configuration = new HashMap<>();
    }

    @NonNull
    public EntityExtensionConfiguration addOrUpdateProperty(
            @NonNull Class<?> propertyType,
            @NonNull String propertyName,
            @Nullable Consumer<ExtensionPropertyConfiguration> configureAction) {
        Objects.requireNonNull(propertyType);
        Objects.requireNonNull(propertyName);

        ExtensionPropertyConfiguration propertyInfo = properties.computeIfAbsent(
                propertyName,
                key -> new ExtensionPropertyConfiguration(this, propertyType, propertyName)
        );
        if (configureAction != null) {
            configureAction.accept(propertyInfo);
        }

        normalizeProperty(propertyInfo);

        if (StringUtils.isNotBlank(propertyInfo.getUi().getLookup().getUrl())) {
            addLookupTextProperty(propertyInfo);
            propertyInfo.getUi().getOnTable().setVisible(false);
        }
        return this;
    }

    private void addLookupTextProperty(ExtensionPropertyConfiguration propertyInfo) {
        String lookupTextPropertyName = propertyInfo.getName() + "_Text";
        ExtensionPropertyConfiguration lookupTextPropertyInfo = properties.computeIfAbsent(
                lookupTextPropertyName,
                key -> new ExtensionPropertyConfiguration(this, String.class, lookupTextPropertyName)
        );

        lookupTextPropertyInfo.setDisplayName(propertyInfo.getDisplayName());
    }

    @NonNull
    public List<ExtensionPropertyConfiguration> getProperties() {
        return new ArrayList<>(properties.values());
    }

    private static void normalizeProperty(ExtensionPropertyConfiguration propertyInfo) {
        if (!propertyInfo.getApi().getOnGet().isAvailable()) {
            propertyInfo.getUi().getOnTable().setVisible(false);
        }

        if (!propertyInfo.getApi().getOnCreate().isAvailable()) {
            propertyInfo.getUi().getOnCreateForm().setVisible(false);
        }

        if (!propertyInfo.getApi().getOnUpdate().isAvailable()) {
            propertyInfo.getUi().getOnEditForm().setVisible(false);
        }
    }

    @NonNull
    public List<Consumer<ObjectExtensionValidationContext>> getValidators() {
        return validators;
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }
}
