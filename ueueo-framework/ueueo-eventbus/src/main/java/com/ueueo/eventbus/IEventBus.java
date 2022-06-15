package com.ueueo.eventbus;

import com.ueueo.IDisposable;
import com.ueueo.eventbus.local.ILocalEventHandler;

import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-20 16:42
 */
public interface IEventBus {
    /**
     * Triggers an event.
     *
     * @param eventData            Related data for the event
     * @param onUnitOfWorkComplete True, to publish the event at the end of the current unit of work, if available
     */
    void publish(Object eventData, Boolean onUnitOfWorkComplete);

    /**
     * Triggers an event.
     *
     * @param eventType            Event type
     * @param eventData            Related data for the event
     * @param onUnitOfWorkComplete
     */
    void publish(Class<?> eventType, Object eventData, Boolean onUnitOfWorkComplete);

    /**
     * Registers to an event.
     * Given action is called for all event occurrences.
     *
     * @param action Action to handle events
     *
     * @return
     */
    IDisposable subscribe(Class<?> eventType, Consumer<Object> action);

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

    /**
     * Registers to an event.
     * Given factory is used to create/release handlers
     *
     * <typeparam name="TEvent">Event type</typeparam>
     * <param name="factory">A factory to create/release handlers</param>
     */
    IDisposable subscribe(Class<?> eventType, IEventHandlerFactory factory);

    /**
     * Unregisters from an event.
     *
     * @param eventType Event type
     * @param action
     * @param
     */
    void unsubscribe(Class<?> eventType, Consumer<Object> action);

    /**
     * @param eventType
     * @param handler   Handler object that is registered before
     * @param
     */
    void unsubscribe(Class<?> eventType, IEventHandler handler);

    void unsubscribe(Class<?> eventType, ILocalEventHandler handler);

    void unsubscribe(Class<?> eventType, IEventHandlerFactory factory);

    /**
     * Unregisters all event handlers of given event type.
     *
     * @param eventType
     * @param
     */
    void unsubscribeAll(Class<?> eventType);
}
