package com.ueueo.eventbus;

import lombok.Getter;

import java.util.List;

/**
 * This <see cref="IEventHandlerFactory"/> implementation is used to handle events
 * by a single instance object.
 *
 * <remarks>
 * This class always gets the same single instance of handler.
 * </remarks>
 */
@Getter
public class SingleInstanceHandlerFactory implements IEventHandlerFactory {
    /**
     * The event handler instance.
     */
    private final IEventHandler handlerInstance;

    /**
     * <param name="handler"></param>
     */
    public SingleInstanceHandlerFactory(IEventHandler handler) {
        handlerInstance = handler;
    }

    @Override
    public IEventHandlerDisposeWrapper getHandler() {
        return new EventHandlerDisposeWrapper(handlerInstance, null);
    }

    @Override
    public boolean isInFactories(List<IEventHandlerFactory> handlerFactories) {
        return handlerFactories.stream().anyMatch(f -> {
            if (f instanceof SingleInstanceHandlerFactory) {
                return ((SingleInstanceHandlerFactory) f).getHandlerInstance().equals(handlerInstance);
            }
            return false;
        });
    }
}
