package com.ueueo.core.exceptionhandling;

import com.ueueo.core.logging.IHasLogLevel;
import com.ueueo.core.logging.LogLevel;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2021-08-26 20:32
 */
@Getter
public class ExceptionNotificationContext {

    /** The exception object. */
    private Exception exception;

    private LogLevel logLevel;
    /** True, if it is handled. */
    private boolean handled;

    public ExceptionNotificationContext(@NonNull Exception exception, LogLevel logLevel, boolean handled) {
        Assert.notNull(exception,"exception 不能为空");
        this.exception = exception;
        if (logLevel != null) {
            this.logLevel = logLevel;
        } else if (exception instanceof IHasLogLevel) {
            this.logLevel = ((IHasLogLevel) exception).getLogLevel();
        }
        this.handled = handled;
    }

}
