package com.ueueo.eventbus.local;

import com.ueueo.ID;
import com.ueueo.disposable.IDisposable;
import com.ueueo.eventbus.*;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.uow.IUnitOfWork;
import com.ueueo.uow.IUnitOfWorkManager;
import com.ueueo.uow.UnitOfWorkEventRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Implements EventBus as Singleton pattern.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class LocalEventBus extends EventBusBase implements ILocalEventBus {

    protected AbpLocalEventBusOptions options;

    protected ConcurrentHashMap<Class<?>, List<IEventHandlerFactory>> handlerFactories;

    public LocalEventBus(
            AbpLocalEventBusOptions options,
            BeanFactory beanFactory,
            ICurrentTenant currentTenant,
            IUnitOfWorkManager unitOfWorkManager,
            IEventHandlerInvoker eventHandlerInvoker) {
        super(beanFactory, currentTenant, unitOfWorkManager, eventHandlerInvoker);
        this.options = options;

        this.handlerFactories = new ConcurrentHashMap<>();
        subscribeHandlers(this.options.getHandlers());
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, IEventHandlerFactory factory) {
        List<IEventHandlerFactory> factories = GetOrCreateHandlerFactories(eventType);
        if (!factory.isInFactories(factories)) {
            factories.add(factory);
        }
        return new EventHandlerFactoryUnregistrar(this, eventType, factory);
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, ILocalEventHandler handler) {
        return super.subscribe(eventType, handler);
    }

    @Override
    public void unsubscribe(Class<?> eventType, Consumer<Object> action) {
        Assert.notNull(action, "Action must not null!");

        List<IEventHandlerFactory> factories = GetOrCreateHandlerFactories(eventType);
        factories.removeIf(factory -> {
            if (factory instanceof SingleInstanceHandlerFactory) {
                IEventHandler actionHandler = ((SingleInstanceHandlerFactory) factory).getHandlerInstance();
                if (actionHandler instanceof ActionEventHandler) {
                    return ((ActionEventHandler) actionHandler).getAction().equals(action);
                }
            }
            return false;
        });
    }

    @Override
    public void unsubscribe(Class<?> eventType, IEventHandler handler) {
        GetOrCreateHandlerFactories(eventType).removeIf(factory ->
                factory instanceof SingleInstanceHandlerFactory &&
                        ((SingleInstanceHandlerFactory) factory).getHandlerInstance().equals(handler));
    }

    @Override
    public void unsubscribe(Class<?> eventType, IEventHandlerFactory factory) {
        GetOrCreateHandlerFactories(eventType).remove(factory);
    }

    @Override
    public void unsubscribeAll(Class<?> eventType) {
        GetOrCreateHandlerFactories(eventType).clear();
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
    public void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandlerFactory factory) {

    }

    @Override
    public void unsubscribeAll(Class<?> eventType, Class<?> genericArgumentType) {

    }

    @Override
    protected void publishToEventBus(Class<?> eventType,Class<?> genericArgumentType,  Object eventData) {
        publish(new LocalEventMessage(ID.valueOf(UUID.randomUUID().toString()), eventData, eventType,genericArgumentType));
    }

    @Override
    protected void addToUnitOfWork(IUnitOfWork unitOfWork, UnitOfWorkEventRecord eventRecord) {
        unitOfWork.addOrReplaceLocalEvent(eventRecord, null);
    }

    public void publish(LocalEventMessage localEventMessage) {
        triggerHandlers( localEventMessage.getEventType(),localEventMessage.getGenericArgumentType(), localEventMessage.getEventData());
    }


    @Override
    public IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, Consumer<Object> action) {
        return null;
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandler handler) {
        return null;
    }

    @Override
    public void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, ILocalEventHandler handler) {

    }

    @Override
    protected List<EventTypeWithEventHandlerFactories> getHandlerFactories(Class<?> eventType, Class<?> genericArgumentType) {
        //TODO by Lee on 2022-07-05 16:22 genericArgumentType还没有处理
        List<EventTypeWithEventHandlerFactories> handlerFactoryList = new ArrayList<>();

        for (Map.Entry<Class<?>, List<IEventHandlerFactory>> handlerFactory : handlerFactories.entrySet().stream().filter(hf -> ShouldTriggerEventForHandler(eventType, hf.getKey())).collect(Collectors.toList())) {
            handlerFactoryList.add(new EventTypeWithEventHandlerFactories(handlerFactory.getKey(), handlerFactory.getValue()));
        }

        return handlerFactoryList;
    }

    private List<IEventHandlerFactory> GetOrCreateHandlerFactories(Class<?> eventType) {
        return handlerFactories.computeIfAbsent(eventType, type -> new ArrayList<>());
    }

    private static boolean ShouldTriggerEventForHandler(Class<?> targetEventType, Class<?> handlerEventType) {
        //Should trigger same type
        if (handlerEventType == targetEventType) {
            return true;
        }

        //Should trigger for inherited types
        if (handlerEventType.isAssignableFrom(targetEventType)) {
            return true;
        }

        return false;
    }


}
