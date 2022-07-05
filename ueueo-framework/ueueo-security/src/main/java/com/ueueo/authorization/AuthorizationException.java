package com.ueueo.authorization;

import com.ueueo.exception.BaseException;
import com.ueueo.exceptionhandling.IHasErrorCode;
import com.ueueo.logging.IHasLogLevel;
import org.slf4j.event.Level;

/**
 * This exception is thrown on an unauthorized request.
 *
 * @author Lee
 * @date 2021-08-26 20:27
 */
public class AuthorizationException extends BaseException implements IHasLogLevel, IHasErrorCode {

    /**
     * Severity of the exception.
     * Default: Warn.
     */
    private Level logLevel;

    @Override
    public Level getLogLevel() {
        return logLevel;
    }

    @Override
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    /** Error code. */
    private String code;

    @Override
    public String getCode() {
        return code;
    }

    /** Creates a new <see cref="AbpAuthorizationException"/> object. */
    public AuthorizationException() {
        this.logLevel = Level.WARN;
    }

    /**
     * Creates a new <see cref="AbpAuthorizationException"/> object.
     *
     * @param message Exception message
     */
    public AuthorizationException(String message) {
        super(message);
        this.logLevel = Level.WARN;
    }

    /**
     * Creates a new <see cref="AbpAuthorizationException"/> object.
     *
     * @param message        Exception message
     * @param innerException Inner exception
     */
    public AuthorizationException(String message, Exception innerException) {
        super(message, innerException);
        this.logLevel = Level.WARN;
    }

    /**
     * Creates a new <see cref="AbpAuthorizationException"/> object.
     *
     * @param message        Exception message
     * @param code           Exception code
     * @param innerException Inner exception
     */
    public AuthorizationException(String message, String code, Exception innerException) {
        super(message, innerException);
        this.code = code;
        this.logLevel = Level.WARN;
    }

}
