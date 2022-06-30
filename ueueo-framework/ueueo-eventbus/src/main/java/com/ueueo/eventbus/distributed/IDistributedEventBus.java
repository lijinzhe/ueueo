package com.ueueo.eventbus.distributed;

import com.ueueo.exception.SystemException;
import com.ueueo.disposable.IDisposable;
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
     *
     * @return
     */
    IDisposable subscribe(Class<?> eventType, IDistributedEventHandler handler);

    /**
     * @param eventType
     * @param eventData
     * @param onUnitOfWorkComplete true
     * @param useOutbox            true
     */
    void publish(Class<?> eventType, Object eventData, Boolean onUnitOfWorkComplete, Boolean useOutbox);

    default ISupportsEventBoxes asSupportsEventBoxes() {
        if (!(this instanceof ISupportsEventBoxes)) {
            throw new SystemException("Given type ({eventBus.GetType().AssemblyQualifiedName}) should implement {nameof(ISupportsEventBoxes)}!");
        }
        return (ISupportsEventBoxes) this;
    }
}
