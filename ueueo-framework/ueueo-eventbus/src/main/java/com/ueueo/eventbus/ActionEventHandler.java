package com.ueueo.eventbus;

import com.ueueo.eventbus.local.ILocalEventHandler;
import com.ueueo.multitenancy.TenantCreatedEto;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * This event handler is an adapter to be able to use an action as <see cref="ILocalEventHandler{TEvent}"/> implementation.
 *
 * <typeparam name="TEvent">Event type</typeparam>
 */
@Getter
public class ActionEventHandler<TEvent> implements ILocalEventHandler<TEvent> {
    /**
     * Function to handle the event.
     */
    private Consumer<TEvent> action;


    /**
     * Creates a new instance of <see cref="ActionEventHandler{TEvent}"/>.
     *
     * <param name="handler">Action to handle the event</param>
     */
    public ActionEventHandler(Consumer<TEvent> handler) {
        IEventBus i = (IEventBus) new Object();
        i.publish(handler,false);
        action = handler;
    }

    /**
     * Handles the event.
     *
     * <param name="eventData"></param>
     */
    @Override
    public void handleEvent(TEvent eventData) {
        action.accept(eventData);
    }
}
