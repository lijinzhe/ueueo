package com.ueueo.core;

import com.ueueo.core.exceptionhandling.IHasErrorCode;
import com.ueueo.core.exceptionhandling.IHasErrorDetails;
import com.ueueo.core.logging.IHasLogLevel;
import com.ueueo.core.logging.LogLevel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-14 09:47
 */
public class BusinessException extends RuntimeException implements IBusinessException, IHasErrorCode, IHasErrorDetails, IHasLogLevel, Serializable {

    private String code;
    private String details;
    private LogLevel logLevel;

    private Map<String, Object> data;

    public BusinessException(String code, String message, String details, Throwable cause, LogLevel logLevel) {
        super(message, cause);
        this.code = code;
        this.details = details;
        this.logLevel = logLevel;
        this.data = new HashMap<>();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }

    @Override
    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public BusinessException withData(String name, Object value) {
        this.data.put(name, value);
        return this;
    }
}

