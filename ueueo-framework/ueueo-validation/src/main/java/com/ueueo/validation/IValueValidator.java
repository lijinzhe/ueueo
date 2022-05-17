package com.ueueo.validation;

/**
 * @author Lee
 * @date 2022-05-17 17:32
 */
public interface IValueValidator {

    String getName();

    Object getProperty(String key);

    void setProperty(String key, Object value);

    boolean isValid(Object value);
}
