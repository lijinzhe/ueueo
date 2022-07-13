package com.ueueo.data.objectextending.modularity;

import com.ueueo.data.objectextending.IBasicObjectExtensionPropertyInfo;
import com.ueueo.data.objectextending.ObjectExtensionPropertyValidationContext;
import com.ueueo.localization.IHasNameWithLocalizableDisplayName;
import com.ueueo.localization.ILocalizableString;
import com.ueueo.localization.LocalizableString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExtensionPropertyConfiguration implements IHasNameWithLocalizableDisplayName, IBasicObjectExtensionPropertyInfo {
    @NonNull
    private EntityExtensionConfiguration entityExtensionConfiguration;

    @NonNull
    private String name;

    @NonNull
    private Class<?> type;

    @NonNull
    private List<Annotation> attributes;

    @NonNull
    private List<Consumer<ObjectExtensionPropertyValidationContext>> validators;

    @Nullable
    private ILocalizableString displayName;

    @NonNull
    private Map<String, Object> configuration;

    /**
     * Single point to enable/disable this property for the clients (UI and API).
     * If this is false, the configuration made in the <see cref="UI"/> and the <see cref="Api"/>
     * properties are not used.
     * Default: true.
     */
    private boolean isAvailableToClients = true;

    @NonNull
    private ExtensionPropertyEntityConfiguration entity;

    @NonNull
    private ExtensionPropertyUiConfiguration ui;

    @NonNull
    private ExtensionPropertyApiConfiguration api;

    /**
     * Uses as the default value if <see cref="DefaultValueFactory"/> was not set.
     */
    @Nullable
    private Object defaultValue;

    /**
     * Used with the first priority to create the default value for the property.
     * Uses to the <see cref="DefaultValue"/> if this was not set.
     */
    @Nullable
    private Supplier<Object> defaultValueFactory;

    public ExtensionPropertyConfiguration(
            @NonNull EntityExtensionConfiguration entityExtensionConfiguration,
            @NonNull Class<?> type,
            @NonNull String name) {
        this.entityExtensionConfiguration = Objects.requireNonNull(entityExtensionConfiguration);
        this.type = Objects.requireNonNull(type);
        this.name = Objects.requireNonNull(name);

        configuration = new HashMap<>();
        attributes = new ArrayList<>();
        validators = new ArrayList<>();

        entity = new ExtensionPropertyEntityConfiguration();
        ui = new ExtensionPropertyUiConfiguration();
        api = new ExtensionPropertyApiConfiguration();
        if (this.type.getAnnotations().length > 0) {
            attributes.addAll(Arrays.asList(this.type.getAnnotations()));
        }
        //        DefaultValue = TypeHelper.GetDefaultValue(Type);
        //TODO 获取类型的默认值
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ILocalizableString getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@Nullable ILocalizableString displayName) {
        this.displayName = displayName;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public List<Annotation> getAttributes() {
        return attributes;
    }

    @Override
    public List<Consumer<ObjectExtensionPropertyValidationContext>> getValidators() {
        return validators;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Supplier<Object> getDefaultValueFactory() {
        return defaultValueFactory;
    }

    @Override
    public void setDefaultValueFactory(Supplier<Object> defaultValueFactory) {
        this.defaultValueFactory = defaultValueFactory;
    }

    public boolean isAvailableToClients() {
        return isAvailableToClients;
    }

    public void setAvailableToClients(boolean availableToClients) {
        isAvailableToClients = availableToClients;
    }

    @NonNull
    public EntityExtensionConfiguration getEntityExtensionConfiguration() {
        return entityExtensionConfiguration;
    }

    @NonNull
    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    @NonNull
    public ExtensionPropertyEntityConfiguration getEntity() {
        return entity;
    }

    @NonNull
    public ExtensionPropertyUiConfiguration getUi() {
        return ui;
    }

    @NonNull
    public ExtensionPropertyApiConfiguration getApi() {
        return api;
    }

//    public String getLocalizationResourceNameOrNull() {
//        Class<?> resourceType = getLocalizationResourceTypeOrNull();
//        if (resourceType == null) {
//            return null;
//        }
//        LocalizationResourceNameAttribute attribute = resourceType.getAnnotation(LocalizationResourceNameAttribute.class);
//        return attribute.name();
//    }

    public Class<?> getLocalizationResourceTypeOrNull() {
        if (displayName != null &&
                displayName instanceof LocalizableString) {
            return ((LocalizableString) displayName).getResourceType();
        }
        return null;
    }
}
