package com.ueueo.data.objectextending;

import com.ueueo.localization.ILocalizableString;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Lee
 * @date 2022-05-23 14:13
 */
public interface IBasicObjectExtensionPropertyInfo {
    String getName();

    Class<?> getType();

    List<Annotation> getAttributes();

    List<Consumer<ObjectExtensionPropertyValidationContext>> getValidators();

    ILocalizableString getDisplayName();

    /**
     * Uses as the default value if <see cref="DefaultValueFactory"/> was not set.
     *
     * @return
     */
    Object getDefaultValue();

    void setDefaultValue(Object defaultValue);

    /**
     * Used with the first priority to create the default value for the property.
     * Uses to the <see cref="DefaultValue"/> if this was not set.
     *
     * @return
     */
    Supplier<Object> getDefaultValueFactory();

    void setDefaultValueFactory(Supplier<Object> defaultValueFactory);
}
