package com.ueueo.eventbus;

/**
 * @author Lee
 * @date 2022-05-30 21:42
 */
public interface IEventHandlerInvoker {
    void invoke(IEventHandler eventHandler, Object eventData, Class<?> eventType);
}
