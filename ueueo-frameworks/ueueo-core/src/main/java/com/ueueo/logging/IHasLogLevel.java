package com.ueueo.logging;

import org.slf4j.event.Level;

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
    Level getLogLevel();

    void setLogLevel(Level logLevel);
}
