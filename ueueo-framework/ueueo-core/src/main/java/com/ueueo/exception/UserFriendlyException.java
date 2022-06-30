package com.ueueo.exception;

import org.slf4j.event.Level;

/**
 * This exception type is directly shown to the user.
 *
 * @author Lee
 * @date 2022-05-14 16:17
 */
public class UserFriendlyException extends BusinessException implements IUserFriendlyException {
    public UserFriendlyException(String code, String message, String details, Throwable cause, Level logLevel) {
        super(code, message, details, cause, logLevel);
    }
}
