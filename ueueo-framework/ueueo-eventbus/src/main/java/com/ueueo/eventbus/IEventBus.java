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
    <TEvent> IDisposable subscribe(Class<TEvent> eventType, IEventHandler handler);

    /**
     * Registers to an event.
     * Given factory is used to create/release handlers
     *
     * <typeparam name="TEvent">Event type</typeparam>
     * <param name="factory">A factory to create/release handlers</param>
     */
    <TEvent> IDisposable subscribe(Class<TEvent> eventType, IEventHandlerFactory factory);

    /**
     * Unregisters from an event.
     *
     * @param eventType Event type
     * @param action
     * @param <TEvent>
     */
    <TEvent> void unsubscribe(Class<TEvent> eventType, Consumer<TEvent> action);

    /**
     * @param eventType
     * @param handler   Handler object that is registered before
     * @param <TEvent>
     */
    <TEvent> void unsubscribe(Class<TEvent> eventType, IEventHandler handler);

    <TEvent> void unsubscribe(Class<TEvent> eventType, ILocalEventHandler<TEvent> handler);

    <TEvent> void unsubscribe(Class<TEvent> eventType, IEventHandlerFactory factory);

    /**
     * Unregisters all event handlers of given event type.
     *
     * @param eventType
     * @param <TEvent>
     */
    <TEvent> void unsubscribeAll(Class<TEvent> eventType);
}
