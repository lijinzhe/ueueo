package com.ueueo.eventbus;

import com.ueueo.eventbus.local.ILocalEventHandler;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * This event handler is an adapter to be able to use an action as <see cref="ILocalEventHandler{TEvent}"/> implementation.
 *
 * <typeparam name="TEvent">Event type</typeparam>
 */
@Getter
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActionEventHandler implements ILocalEventHandler {
    /**
     * Function to handle the event.
     */
    private Consumer<Object> action;

    /**
     * Creates a new instance of <see cref="ActionEventHandler{TEvent}"/>.
     *
     * <param name="handler">Action to handle the event</param>
     */
    public ActionEventHandler(Consumer<Object> handler) {
        action = handler;
    }

    /**
     * Handles the event.
     *
     * <param name="eventData"></param>
     */
    @Override
    public void handleEvent(Object eventData) {
        action.accept(eventData);
    }
}
