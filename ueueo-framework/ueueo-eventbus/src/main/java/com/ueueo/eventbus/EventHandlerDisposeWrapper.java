package com.ueueo.eventbus;

import lombok.Getter;

@Getter
public class EventHandlerDisposeWrapper implements IEventHandlerDisposeWrapper {

    private IEventHandler eventHandler;

    private Runnable disposeAction;

    public EventHandlerDisposeWrapper(IEventHandler eventHandler, Runnable disposeAction) {
        this.disposeAction = disposeAction;
        this.eventHandler = eventHandler;
    }

    @Override
    public void dispose() {
        if (disposeAction != null) {
            disposeAction.run();
        }
    }
}
