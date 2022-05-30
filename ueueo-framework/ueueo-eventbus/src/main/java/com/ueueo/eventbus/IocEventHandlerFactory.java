package com.ueueo.eventbus;

import com.ueueo.IDisposable;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;

/**
 * This <see cref="IEventHandlerFactory"/> implementation is used to get/release
 * handlers using Ioc.
 */
@Data
public class IocEventHandlerFactory implements IEventHandlerFactory, IDisposable {
    private Class<? extends IEventHandler> handlerType;

    protected BeanFactory beanFactory;

    public IocEventHandlerFactory(BeanFactory beanFactory, Class<? extends IEventHandler> handlerType) {
        this.beanFactory = beanFactory;
        this.handlerType = handlerType;
    }

    /**
     * Resolves handler object from Ioc container.
     *
     * <returns>Resolved handler object</returns>
     */
    @Override
    public IEventHandlerDisposeWrapper getHandler() {
        return new EventHandlerDisposeWrapper(beanFactory.getBean(getHandlerType()), null);
    }

    @Override
    public boolean isInFactories(List<IEventHandlerFactory> handlerFactories) {
        return handlerFactories.stream().anyMatch(f -> {
            if (f instanceof IocEventHandlerFactory) {
                return ((IocEventHandlerFactory) f).getHandlerType().equals(handlerType);
            }
            return false;
        });
    }

    @Override
    public void dispose() {

    }
}
