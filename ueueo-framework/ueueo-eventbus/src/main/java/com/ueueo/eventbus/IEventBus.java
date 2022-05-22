package com.ueueo.eventbus;

import com.ueueo.IDisposable;

import java.util.function.Consumer;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 16:42
 */
public interface IEventBus {
    /**
     * Triggers an event.
     *
     * @param eventData            Related data for the event
     * @param onUnitOfWorkComplete True, to publish the event at the end of the current unit of work, if available
     * @param <TEvent>             Event type
     */
    <TEvent> void publish(TEvent eventData, boolean onUnitOfWorkComplete);

    /**
     * Triggers an event.
     *
     * @param eventType            Event type
     * @param eventData            Related data for the event
     * @param onUnitOfWorkComplete True, to publish the event at the end of the current unit of work, if available
     */
    void publish(Class<?> eventType, Object eventData, boolean onUnitOfWorkComplete);

    /**
     * Registers to an event.
     * Given action is called for all event occurrences.
     *
     * @param action   Action to handle events
     * @param <TEvent> Event type
     *
     * @return
     */
    <TEvent> IDisposable subscribe(Class<TEvent> eventType, Consumer<TEvent> action);

    /**
     * Registers to an event.
     * Same (given) instance of the handler is used for all event occurrences.
     *
     * @param eventType Event type
     * @param handler   Object to handle the event
     *
     * @return
     */
    IDisposable subscribe(Class<?> eventType, IEventHandler handler);

}
