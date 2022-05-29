package com.ueueo.data;

import com.ueueo.AbpException;

/**
 * @author Lee
 * @date 2022-05-29 14:52
 */
public class AbpDbConcurrencyException extends AbpException {

    public AbpDbConcurrencyException() {
        super();
    }

    public AbpDbConcurrencyException(String message) {
        super(message);
    }

    public AbpDbConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
