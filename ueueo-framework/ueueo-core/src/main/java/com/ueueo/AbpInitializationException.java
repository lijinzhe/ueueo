package com.ueueo;

/**
 * @author Lee
 * @date 2021-08-24 14:45
 */
public class AbpInitializationException extends AbpException {
    public AbpInitializationException() {}

    public AbpInitializationException(String message) {
        super(message);
    }

    public AbpInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
