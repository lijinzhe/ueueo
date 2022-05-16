package com.ueueo.eventbus.distributed;

import com.ueueo.eventbus.IEventHandler;

import java.util.concurrent.Future;

/**
 *
 * @author Lee
 * @date 2022-05-16 10:46
 */
public interface IDistributedEventHandler<TEvent> extends IEventHandler {
    /**
     * Handler handles the event by implementing this method.
     *
     * @param eventData Event data
     *
     * @return
     */
    Future<?> handleEventAsync(TEvent eventData);
}
