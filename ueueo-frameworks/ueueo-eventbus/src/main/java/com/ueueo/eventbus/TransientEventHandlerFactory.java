package com.ueueo.eventbus;

import com.ueueo.disposable.IDisposable;
import lombok.Getter;

import java.util.List;

/**
 * This <see cref="IEventHandlerFactory"/> implementation is used to handle events
 * by a transient instance object.
 *
 * <remarks>
 * This class always creates a new transient instance of the handler type.
 * </remarks>
 */
@Getter
public class TransientEventHandlerFactory implements IEventHandlerFactory {

    private final Class<? extends IEventHandler> handlerType;

    public TransientEventHandlerFactory(Class<? extends IEventHandler> handlerType) {
        this.handlerType = handlerType;
    }

    /**
     * Creates a new instance of the handler object.
     *
     * <returns>The handler object</returns>
     */
    @Override
    public IEventHandlerDisposeWrapper getHandler() {
        IEventHandler handler = createHandler();
        return new EventHandlerDisposeWrapper(handler, () -> {
            if (handler instanceof IDisposable) {
                ((IDisposable) handler).dispose();
            }
        });
    }

    @Override
    public boolean isInFactories(List<IEventHandlerFactory> handlerFactories) {
        return handlerFactories.stream().anyMatch(f -> {
            if (f instanceof TransientEventHandlerFactory) {
                return ((TransientEventHandlerFactory) f).getHandlerType().equals(handlerType);
            }
            return false;
        });
    }

    protected IEventHandler createHandler() {
        try {
            return (IEventHandler) handlerType.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
