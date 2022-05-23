package com.ueueo.data.objectextending;

import com.ueueo.localization.ILocalizableString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Consumer;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 14:13
 */
public interface IBasicObjectExtensionPropertyInfo {
    String getName();

    Type getType();

    List<Annotation> getAttributes();

    List<Consumer<ObjectExtensionPropertyValidationContext>> getValidators();

    ILocalizableString getDisplayName();

    Object getDefaultValue();

    void setDefaultValue(Object defaultValue);

    Consumer<Object> getDefaultValueFactory();

    void setDefaultValueFactory(Consumer<Object> defaultValueFactory);
}
