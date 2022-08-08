package com.ueueo.exceptionhandling;

import com.ueueo.logging.IHasLogLevel;
import com.ueueo.util.Check;
import lombok.Getter;
import org.slf4j.event.Level;
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

    private Level logLevel;
    /** True, if it is handled. */
    private boolean handled;

    public ExceptionNotificationContext(@NonNull Exception exception, Level logLevel, boolean handled) {
        Check.notNull(exception, "exception");
        this.exception = exception;
        if (logLevel != null) {
            this.logLevel = logLevel;
        } else if (exception instanceof IHasLogLevel) {
            this.logLevel = ((IHasLogLevel) exception).getLogLevel();
        }
        this.handled = handled;
    }

}
