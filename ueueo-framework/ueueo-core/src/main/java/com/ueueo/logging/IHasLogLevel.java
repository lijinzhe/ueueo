package com.ueueo.logging;

/**
 * Interface to define a <see cref="LogLevel"/> property (see <see cref="LogLevel"/>).
 *
 * @author Lee
 * @date 2021-08-26 20:28
 */
public interface IHasLogLevel {
    /**
     * Log severity.
     *
     * @return
     */
    LogLevel getLogLevel();

    void setLogLevel(LogLevel logLevel);
}
