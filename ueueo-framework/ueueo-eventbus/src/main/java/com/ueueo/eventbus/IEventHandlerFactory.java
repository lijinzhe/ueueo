package com.ueueo.eventbus;

import java.util.List;

/**
 * Defines an interface for factories those are responsible to create/get and release of event handlers.
 */
public interface IEventHandlerFactory {
    /**
     * Gets an event handler.
     *
     * <returns>The event handler</returns>
     */
    IEventHandlerDisposeWrapper getHandler();

    boolean isInFactories(List<IEventHandlerFactory> handlerFactories);
}
