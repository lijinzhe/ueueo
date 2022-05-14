package com.ueueo.core;

/**
 * Base exception type for those are thrown by Abp system for Abp specific exceptions.
 *
 * @author Lee
 * @date 2021-08-18 21:08
 */
public class AbpException extends Exception {

    public AbpException() {
        super();
    }

    public AbpException(String message) {
        super(message);
    }

    public AbpException(String message, Throwable cause) {
        super(message, cause);
    }
}
