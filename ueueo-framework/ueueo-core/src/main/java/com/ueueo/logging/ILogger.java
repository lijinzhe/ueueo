package com.ueueo.logging;

import java.util.function.BiFunction;

/**
 * Represents a type used to perform logging.
 * Aggregates most logging patterns to a single method.
 *
 * @author Lee
 * @date 2022-05-14 13:26
 */
public interface ILogger {

    /**
     * Checks if the given logLevel is enabled.
     *
     * @param logLevel Level to be checked.
     *
     * @return true if enabled.
     */
    boolean getIsEnabled(LogLevel logLevel);

    /**
     * Writes a log entry.
     *
     * @param logLevel  Entry will be written on this level.
     * @param eventId   Id of the event.
     * @param state     The entry to be written. Can be also an object.
     * @param exception The exception related to this entry.
     * @param formatter Function to create a System.String message of the state and exception.
     * @param <TState>  The type of the object to be written.
     */
    <TState> void log(LogLevel logLevel, EventId eventId, TState state, Exception exception, BiFunction<TState, Exception, String> formatter);
}
