package com.ueueo.eventbus.distributed;

import com.ueueo.disposable.IDisposable;
import com.ueueo.eventbus.IEventHandler;
import com.ueueo.eventbus.IEventHandlerFactory;
import com.ueueo.eventbus.local.ILocalEventBus;
import com.ueueo.eventbus.local.ILocalEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class LocalDistributedEventBus implements IDistributedEventBus {
    private ILocalEventBus _localEventBus;

    protected BeanFactory beanFactory;

    protected AbpDistributedEventBusOptions AbpDistributedEventBusOptions;

    public LocalDistributedEventBus(
            ILocalEventBus localEventBus,
            BeanFactory beanFactory,
            AbpDistributedEventBusOptions distributedEventBusOptions) {
        _localEventBus = localEventBus;
        this.beanFactory = beanFactory;
        AbpDistributedEventBusOptions = distributedEventBusOptions;
        subscribe(distributedEventBusOptions.getHandlers());
    }

    public void subscribe(List<? extends Class<? extends IEventHandler>> handlers) {
        for (Class<? extends IEventHandler> handler : handlers) {
            //TODO 需要替换java 实现
            //            var interfaces = handler.GetInterfaces();
            //            for (var @interface in interfaces)
            //            {
            //                if (!typeof(IEventHandler).GetTypeInfo().IsAssignableFrom(@interface))
            //                {
            //                    continue;
            //                }
            //
            //                var genericArgs = @interface.GetGenericArguments();
            //                if (genericArgs.Length == 1)
            //                {
            //                    Subscribe(genericArgs[0], new IocEventHandlerFactory(ServiceScopeFactory, handler));
            //                }
            //            }
        }
    }

    @Override
    public void publish(Object eventData, Boolean onUnitOfWorkComplete) {
        _localEventBus.publish(eventData, onUnitOfWorkComplete);
    }

    @Override
    public void publish(Class<?> eventType, Class<?> genericArgumentType, Object eventData, Boolean onUnitOfWorkComplete) {
        _localEventBus.publish(eventType, genericArgumentType, eventData, onUnitOfWorkComplete);
    }

    @Override
    public void publish(Class<?> eventType, Class<?> genericArgumentType, Object eventData, Boolean onUnitOfWorkComplete, Boolean useOutbox) {
        _localEventBus.publish(eventData, onUnitOfWorkComplete);
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, Consumer<Object> action) {
        return _localEventBus.subscribe(eventType, action);
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, IEventHandler handler) {
        return _localEventBus.subscribe(eventType, handler);
    }

    public IDisposable subscribe(Class<?> eventType, ILocalEventHandler handler) {
        return _localEventBus.subscribe(eventType, handler);
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, IEventHandlerFactory factory) {
        return _localEventBus.subscribe(eventType, factory);
    }

    @Override
    public void unsubscribe(Class<?> eventType, Consumer<Object> action) {
        _localEventBus.unsubscribe(eventType, action);
    }

    @Override
    public void unsubscribe(Class<?> eventType, IEventHandler handler) {
        _localEventBus.unsubscribe(eventType, handler);
    }

    @Override
    public void unsubscribe(Class<?> eventType, ILocalEventHandler handler) {
        _localEventBus.unsubscribe(eventType, handler);
    }

    @Override
    public void unsubscribe(Class<?> eventType, IEventHandlerFactory factory) {
        _localEventBus.unsubscribe(eventType, factory);
    }

    @Override
    public void unsubscribeAll(Class<?> eventType) {
        _localEventBus.unsubscribeAll(eventType);
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, IDistributedEventHandler handler) {
        return null;
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, Consumer<Object> action) {
        return _localEventBus.subscribe(eventType, action);
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandler handler) {
        return null;
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandlerFactory factory) {
        return null;
    }

    @Override
    public void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, Consumer<Object> action) {

    }

    @Override
    public void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandler handler) {

    }

    @Override
    public void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, ILocalEventHandler handler) {

    }

    @Override
    public void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandlerFactory factory) {

    }

    @Override
    public void unsubscribeAll(Class<?> eventType, Class<?> genericArgumentType) {

    }
}
