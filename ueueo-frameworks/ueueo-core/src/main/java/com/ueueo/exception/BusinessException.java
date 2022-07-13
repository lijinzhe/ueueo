package com.ueueo.exception;

import com.ueueo.exceptionhandling.IHasErrorCode;
import com.ueueo.exceptionhandling.IHasErrorDetails;
import com.ueueo.logging.IHasLogLevel;
import org.slf4j.event.Level;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-14 09:47
 */
public class BusinessException extends BaseException implements IBusinessException, IHasErrorCode, IHasErrorDetails, IHasLogLevel, Serializable {

    /** Error code. */
    private String code;

    private String details;
    /**
     * Severity of the exception.
     * Default: INFO.
     */
    private Level logLevel;

    public BusinessException() {
        this(null);
    }

    public BusinessException(String message) {
        this(message, "");
    }

    public BusinessException(String message, String code) {
        this(message, code, null);
    }

    public BusinessException(String message, Throwable cause) {
        this(message, "", null, cause, Level.INFO);
    }

    public BusinessException(String message, String code, String details) {
        this(message, code, details, null, Level.INFO);
    }

    public BusinessException(String message, String code, String details, Throwable cause, Level logLevel) {
        super(message, cause);
        this.code = code;
        this.details = details;
        this.logLevel = logLevel != null ? logLevel : Level.INFO;
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
    public Level getLogLevel() {
        return logLevel;
    }

    @Override
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }
}

