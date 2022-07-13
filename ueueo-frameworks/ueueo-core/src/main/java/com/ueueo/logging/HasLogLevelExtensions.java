package com.ueueo.logging;

import org.slf4j.event.Level;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-05-14 13:18
 */
public class HasLogLevelExtensions {

    public static <TException extends IHasLogLevel> TException withLogLevel(@NonNull TException exception, Level logLevel) {
        Assert.notNull(exception, "exception 不能为空");
        exception.setLogLevel(logLevel);
        return exception;
    }
}
