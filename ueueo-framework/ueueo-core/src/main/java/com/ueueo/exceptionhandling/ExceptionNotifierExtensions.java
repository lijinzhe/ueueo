package com.ueueo.exceptionhandling;

import org.slf4j.event.Level;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * @author Lee
 * @date 2022-05-29 12:50
 */
public final class ExceptionNotifierExtensions {

    public static void notify(@NonNull IExceptionNotifier exceptionNotifier,
                              @NonNull Exception exception,
                              Level logLevel,
                              boolean handled) {
        Objects.requireNonNull(exceptionNotifier);

        exceptionNotifier.notify(new ExceptionNotificationContext(exception, logLevel, handled));
    }
}
