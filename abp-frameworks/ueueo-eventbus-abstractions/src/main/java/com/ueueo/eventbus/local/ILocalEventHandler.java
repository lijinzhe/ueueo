package com.ueueo.eventbus.local;

import com.ueueo.eventbus.IEventHandler;

/**
 * @author Lee
 * @date 2022-05-16 10:45
 */
public interface ILocalEventHandler extends IEventHandler {
    /**
     * Handler handles the event by implementing this method.
     *
     * @param eventData Event data
     *
     * @return
     */
    void handleEvent(Object eventData);
}
