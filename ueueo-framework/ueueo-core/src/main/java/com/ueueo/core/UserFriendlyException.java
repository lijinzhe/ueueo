package com.ueueo.core;

import com.ueueo.core.logging.LogLevel;

/**
 * This exception type is directly shown to the user.
 *
 * @author Lee
 * @date 2022-05-14 16:17
 */
public class UserFriendlyException extends BusinessException implements IUserFriendlyException{
    public UserFriendlyException(String code, String message, String details, Throwable cause, LogLevel logLevel) {
        super(code, message, details, cause, logLevel);
    }
}
