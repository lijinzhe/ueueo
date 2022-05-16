package com.ueueo.exceptionhandling;

import org.springframework.lang.NonNull;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-14 15:32
 */
public interface IExceptionSubscriber {
    void handle(@NonNull ExceptionNotificationContext context);
}
