package com.ueueo.data.objectextending;

import com.ueueo.data.annotations.ValidationAttribute;
import com.ueueo.data.objectextending.modularity.ExtensionPropertyLookupConfiguration;
import com.ueueo.localization.IHasNameWithLocalizableDisplayName;
import com.ueueo.localization.ILocalizableString;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 14:08
 */
public class ObjectExtensionPropertyInfo implements IHasNameWithLocalizableDisplayName, IBasicObjectExtensionPropertyInfo {

    private ObjectExtensionInfo objectExtension;
    private String name;
    private Class<?> type;
    private List<Annotation> attributes;
    private List<Consumer<ObjectExtensionPropertyValidationContext>> validators;

    private ILocalizableString displayName;

    private Boolean checkPairDefinitionOnMapping;

    private Map<Object, Object> configuration;

    private Object defaultValue;

    private Supplier<Object> defaultValueFactory;

    private ExtensionPropertyLookupConfiguration lookup;

    public ObjectExtensionPropertyInfo(ObjectExtensionInfo objectExtension, Class<?> type, String name) {
        this.objectExtension = objectExtension;
        this.name = name;
        this.type = type;

        this.configuration = new HashMap<>();
        this.attributes = new ArrayList<>();
        this.validators = new ArrayList<>();

        //TODO 获取Attributes 注解
        //        Attributes.AddRange(ExtensionPropertyHelper.GetDefaultAttributes(Type));
        //        DefaultValue = TypeHelper.GetDefaultValue(Type);
        this.lookup = new ExtensionPropertyLookupConfiguration();
    }

    @Override
    public Object getDefaultValue() {
        if (defaultValueFactory != null) {
            return defaultValueFactory.get();
        }
        return defaultValue;
    }

    @Override
    public void setDefaultValueFactory(Supplier<Object> defaultValueFactory) {
        this.defaultValueFactory = defaultValueFactory;
    }

    @Override
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return name;
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

    public void setLookup(ExtensionPropertyLookupConfiguration lookup) {
        this.lookup = lookup;
    }

    @Override
    public Supplier<Object> getDefaultValueFactory() {
        return defaultValueFactory;
    }

    @Override
    public ILocalizableString getDisplayName() {
        return displayName;
    }

    public void setDisplayName(ILocalizableString displayName) {
        this.displayName = displayName;
    }

    public List<ValidationAttribute> getValidationAttributes() {
        return attributes.stream().filter(annotation -> annotation instanceof ValidationAttribute)
                .map(annotation -> (ValidationAttribute) annotation)
                .collect(Collectors.toList());
    }

    public ObjectExtensionInfo getObjectExtension() {
        return objectExtension;
    }

    public Boolean getCheckPairDefinitionOnMapping() {
        return checkPairDefinitionOnMapping;
    }

    public Map<Object, Object> getConfiguration() {
        return configuration;
    }

    public ExtensionPropertyLookupConfiguration getLookup() {
        return lookup;
    }
}
