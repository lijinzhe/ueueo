package com.ueueo;

import com.ueueo.exceptionhandling.IHasData;

import java.util.HashMap;
import java.util.Map;

/**
 * Base exception type for those are thrown by Abp system for Abp specific exceptions.
 *
 * @author Lee
 * @date 2021-08-18 21:08
 */
public class AbpException extends RuntimeException implements IHasData {

    protected final Map<String, Object> data = new HashMap<>();

    public AbpException() {
        super();
    }

    public AbpException(String message) {
        super(message);
    }

    public AbpException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbpException withData(String name, Object value) {
        this.data.put(name, value);
        return this;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }
}
