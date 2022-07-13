package com.ueueo.eventbus;

import com.ueueo.disposable.IDisposable;
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
    default void publish(Object eventData, Boolean onUnitOfWorkComplete) {
        if (eventData instanceof IEventDataWithInheritableGenericArgument) {
            publish(eventData.getClass(), ((IEventDataWithInheritableGenericArgument) eventData).getGenericArgumentType(), eventData, onUnitOfWorkComplete);
        } else {
            publish(eventData.getClass(), null, eventData, onUnitOfWorkComplete);
        }
    }

    /**
     * Triggers an event.
     *
     * @param eventType            Event type
     * @param eventData            Related data for the event
     * @param onUnitOfWorkComplete
     */
    void publish(Class<?> eventType, Class<?> genericArgumentType, Object eventData, Boolean onUnitOfWorkComplete);

    /**
     * Registers to an event.
     * Given action is called for all event occurrences.
     *
     * @param action Action to handle events
     *
     * @return
     */
    default IDisposable subscribe(Class<?> eventType, Consumer<Object> action) {
        return subscribe(eventType, null, action);
    }

    IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, Consumer<Object> action);

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

    IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandler handler);

    /**
     * Registers to an event.
     * Given factory is used to create/release handlers
     *
     * <typeparam name="TEvent">Event type</typeparam>
     * <param name="factory">A factory to create/release handlers</param>
     */
    IDisposable subscribe(Class<?> eventType, IEventHandlerFactory factory);

    IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandlerFactory factory);

    /**
     * Unregisters from an event.
     *
     * @param eventType Event type
     * @param action
     * @param
     */
    void unsubscribe(Class<?> eventType, Consumer<Object> action);

    void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, Consumer<Object> action);

    /**
     * @param eventType
     * @param handler   Handler object that is registered before
     * @param
     */
    void unsubscribe(Class<?> eventType, IEventHandler handler);

    void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandler handler);

    void unsubscribe(Class<?> eventType, ILocalEventHandler handler);

    void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, ILocalEventHandler handler);

    void unsubscribe(Class<?> eventType, IEventHandlerFactory factory);

    void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandlerFactory factory);

    /**
     * Unregisters all event handlers of given event type.
     *
     * @param eventType
     * @param
     */
    void unsubscribeAll(Class<?> eventType);

    void unsubscribeAll(Class<?> eventType, Class<?> genericArgumentType);
}
