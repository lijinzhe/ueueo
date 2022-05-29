package com.ueueo.exceptionhandling;

import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-29 12:46
 */
public interface IExceptionNotifier {
    void notify(@NonNull ExceptionNotificationContext context);
}
