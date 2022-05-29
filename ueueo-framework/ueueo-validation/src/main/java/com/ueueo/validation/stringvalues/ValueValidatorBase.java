package com.ueueo.validation.stringvalues;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-29 16:38
 */
public abstract class ValueValidatorBase implements IValueValidator {

    private final Map<String, Object> properties;

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

    protected ValueValidatorBase() {
        properties = new HashMap<>();
    }
}
