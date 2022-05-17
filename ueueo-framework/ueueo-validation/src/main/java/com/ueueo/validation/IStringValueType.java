package com.ueueo.validation;

/**
 * @author Lee
 * @date 2022-05-17 17:31
 */
public interface IStringValueType {

    String getName();

    Object getProperty(String key);

    void setProperty(String key, Object value);

    IValueValidator getValidator();

    void setValidator(IValueValidator validator);
}
