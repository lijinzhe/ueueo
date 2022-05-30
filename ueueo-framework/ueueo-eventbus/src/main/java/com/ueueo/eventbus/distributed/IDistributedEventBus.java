package com.ueueo.eventbus.distributed;

import com.ueueo.IDisposable;
import com.ueueo.eventbus.IEventBus;

/**
 * @author Lee
 * @date 2022-05-20 16:41
 */
public interface IDistributedEventBus extends IEventBus {
    /**
     * Registers to an event.
     * Same (given) instance of the handler is used for all event occurrences.
     *
     * @param eventType Event type
     * @param handler   Object to handle the event
     * @param <TEvent>  Event type
     *
     * @return
     */
    <TEvent> IDisposable subscribe(Class<TEvent> eventType, IDistributedEventHandler<TEvent> handler);

    /**
     * @param eventType
     * @param eventData
     * @param onUnitOfWorkComplete true
     * @param useOutbox            true
     * @param <TEvent>
     */
    <TEvent> void publish(Class<TEvent> eventType, Object eventData, Boolean onUnitOfWorkComplete, Boolean useOutbox);

}
