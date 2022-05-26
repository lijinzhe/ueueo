package com.ueueo;

/**
 * @author Lee
 * @date 2021-08-24 14:49
 */
public class AbpShutdownException extends AbpException {
    public AbpShutdownException() {
    }

    public AbpShutdownException(String message) {
        super(message);
    }

    public AbpShutdownException(String message, Throwable cause) {
        super(message, cause);
    }
}
