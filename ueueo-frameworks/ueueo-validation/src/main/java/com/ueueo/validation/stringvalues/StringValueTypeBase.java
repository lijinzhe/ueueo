package com.ueueo.validation.stringvalues;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-29 16:43
 */
@Data
public abstract class StringValueTypeBase implements IStringValueType {

    private final Map<String, Object> properties;

    private IValueValidator validator;

    @Override
    public String getName() {
        return getClass().getAnnotation(ValueValidatorAttribute.class).name();
    }

    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    protected StringValueTypeBase(IValueValidator validator) {
        this.validator = validator;
        this.properties = new HashMap<>();
    }

    protected StringValueTypeBase() {
        this(new AlwaysValidValueValidator());
    }

}
