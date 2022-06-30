package com.ueueo.eventbus;

import com.ueueo.disposable.IDisposable;

/**
 * Used to unregister a <see cref="IEventHandlerFactory"/> on <see cref="Dispose"/> method.
 */
public class EventHandlerFactoryUnregistrar implements IDisposable {
    private IEventBus eventBus;
    private Class<?> eventType;
    private IEventHandlerFactory factory;

    public EventHandlerFactoryUnregistrar(IEventBus eventBus, Class<?> eventType, IEventHandlerFactory factory) {
        this.eventBus = eventBus;
        this.eventType = eventType;
        this.factory = factory;
    }

    @Override
    public void dispose() {
        eventBus.unsubscribe(eventType, factory);
    }
}
