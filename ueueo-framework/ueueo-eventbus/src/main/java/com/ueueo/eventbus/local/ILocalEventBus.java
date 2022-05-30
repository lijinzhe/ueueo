package com.ueueo.eventbus.local;

import com.ueueo.IDisposable;
import com.ueueo.eventbus.IEventBus;

/**
 * Defines interface of the event bus.
 */
public interface ILocalEventBus extends IEventBus {
    /**
     * Registers to an event.
     * Same (given) instance of the handler is used for all event occurrences.
     *
     * <typeparam name="TEvent">Event type</typeparam>
     * <param name="handler">Object to handle the event</param>
     */
    <TEvent> IDisposable subscribe(ILocalEventHandler<TEvent> handler);
}
