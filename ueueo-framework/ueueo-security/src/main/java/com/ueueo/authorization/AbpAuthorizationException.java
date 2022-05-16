package com.ueueo.authorization;

import com.ueueo.AbpException;
import com.ueueo.exceptionhandling.IHasErrorCode;
import com.ueueo.logging.IHasLogLevel;
import com.ueueo.logging.LogLevel;

/**
 * @author Lee
 * @date 2021-08-26 20:27
 */
public class AbpAuthorizationException extends AbpException implements IHasLogLevel, IHasErrorCode {

    /**
     * Severity of the exception.
     * Default: Warn.
     */
    private LogLevel logLevel;

    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }

    @Override
    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    /** Error code. */
    private String code;

    @Override
    public String getCode() {
        return code;
    }

    /** Creates a new <see cref="AbpAuthorizationException"/> object. */
    public AbpAuthorizationException() {
        this.logLevel = LogLevel.WARN;
    }

    /**
     * Creates a new <see cref="AbpAuthorizationException"/> object.
     *
     * @param message Exception message
     */
    public AbpAuthorizationException(String message) {
        super(message);
        this.logLevel = LogLevel.WARN;
    }

    /**
     * Creates a new <see cref="AbpAuthorizationException"/> object.
     *
     * @param message        Exception message
     * @param innerException Inner exception
     */
    public AbpAuthorizationException(String message, Exception innerException) {
        super(message, innerException);
        this.logLevel = LogLevel.WARN;
    }

    /**
     * Creates a new <see cref="AbpAuthorizationException"/> object.
     *
     * @param message        Exception message
     * @param code           Exception code
     * @param innerException Inner exception
     */
    public AbpAuthorizationException(String message, String code, Exception innerException) {
        super(message, innerException);
        this.code = code;
        this.logLevel = LogLevel.WARN;
    }

}
