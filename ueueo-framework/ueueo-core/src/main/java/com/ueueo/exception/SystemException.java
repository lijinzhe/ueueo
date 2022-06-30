package com.ueueo.exception;

import com.ueueo.exceptionhandling.IHasData;

import java.util.HashMap;
import java.util.Map;

/**
 * Base exception type for those are thrown by Abp system for Abp specific exceptions.
 *
 * @author Lee
 * @date 2021-08-18 21:08
 */
public class SystemException extends RuntimeException implements IHasData {

    protected final Map<String, Object> data = new HashMap<>();

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException withData(String name, Object value) {
        this.data.put(name, value);
        return this;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }
}
