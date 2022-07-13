package com.ueueo.exception;

import com.ueueo.exceptionhandling.IHasData;

import java.util.HashMap;
import java.util.Map;

/**
 * Base exception type for those are thrown by system for specific exceptions.
 *
 * @author Lee
 * @date 2021-08-18 21:08
 */
public class BaseException extends RuntimeException implements IHasData {

    protected final Map<String, Object> data = new HashMap<>();

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException withData(String name, Object value) {
        this.data.put(name, value);
        return this;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }
}
