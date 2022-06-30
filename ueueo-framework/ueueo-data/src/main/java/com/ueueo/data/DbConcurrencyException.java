package com.ueueo.data;

import com.ueueo.SystemException;

/**
 * @author Lee
 * @date 2022-05-29 14:52
 */
public class DbConcurrencyException extends SystemException {

    public DbConcurrencyException() {
        super();
    }

    public DbConcurrencyException(String message) {
        super(message);
    }

    public DbConcurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
