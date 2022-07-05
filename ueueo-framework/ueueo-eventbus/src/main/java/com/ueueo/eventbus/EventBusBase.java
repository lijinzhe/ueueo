package com.ueueo.eventbus;

import com.ueueo.ID;
import com.ueueo.disposable.IDisposable;
import com.ueueo.eventbus.distributed.InboxConfig;
import com.ueueo.eventbus.local.ILocalEventHandler;
import com.ueueo.exception.BaseException;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.multitenancy.IMultiTenant;
import com.ueueo.uow.EventOrderGenerator;
import com.ueueo.uow.IUnitOfWork;
import com.ueueo.uow.IUnitOfWorkManager;
import com.ueueo.uow.UnitOfWorkEventRecord;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class EventBusBase implements IEventBus {

    protected BeanFactory beanFactory;

    protected ICurrentTenant currentTenant;

    protected IUnitOfWorkManager unitOfWorkManager;

    protected IEventHandlerInvoker eventHandlerInvoker;

    protected EventBusBase(
            BeanFactory beanFactory,
            ICurrentTenant currentTenant,
            IUnitOfWorkManager unitOfWorkManager,
            IEventHandlerInvoker eventHandlerInvoker) {
        this.beanFactory = beanFactory;
        this.currentTenant = currentTenant;
        this.unitOfWorkManager = unitOfWorkManager;
        this.eventHandlerInvoker = eventHandlerInvoker;
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, Consumer<Object> action) {
        return subscribe(eventType, new ActionEventHandler(action));
    }

    @Override
    public IDisposable subscribe(Class<?> eventType, IEventHandler handler) {
        return subscribe(eventType, new SingleInstanceHandlerFactory(handler));
    }

    public IDisposable subscribe(Class<?> eventType, Class<? extends IEventHandler> eventHandlerType) {
        return subscribe(eventType, new TransientEventHandlerFactory(eventHandlerType));
    }

    @Override
    public abstract IDisposable subscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandlerFactory factory);

    @Override
    public abstract void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, Consumer<Object> action);

    @Override
    public abstract void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandler handler);

    @Override
    public void unsubscribe(Class<?> eventType, ILocalEventHandler handler) {
        unsubscribe(eventType, handler);
    }

    @Override
    public abstract void unsubscribe(Class<?> eventType, Class<?> genericArgumentType, IEventHandlerFactory factory);

    @Override
    public abstract void unsubscribeAll(Class<?> eventType, Class<?> genericArgumentType);

    @Override
    public void publish(Class<?> eventType, Class<?> genericArgumentType, Object eventData, Boolean onUnitOfWorkComplete) {
        if (onUnitOfWorkComplete && unitOfWorkManager.getCurrent() != null) {
            addToUnitOfWork(
                    unitOfWorkManager.getCurrent(),
                    new UnitOfWorkEventRecord(eventType, genericArgumentType, eventData, EventOrderGenerator.getNext(), null)
            );
            return;
        }

        publishToEventBus(eventType, genericArgumentType, eventData);
    }

    protected abstract void publishToEventBus(Class<?> eventType, Class<?> genericArgumentType, Object eventData);

    protected abstract void addToUnitOfWork(IUnitOfWork unitOfWork, UnitOfWorkEventRecord eventRecord);

    public void triggerHandlers(Class<?> eventType, Class<?> genericArgumentType, Object eventData) {
        List<Exception> exceptions = new ArrayList<>();

        triggerHandlers(eventType, genericArgumentType, eventData, exceptions, null);

        if (!exceptions.isEmpty()) {
            throwOriginalExceptions(eventType, genericArgumentType, exceptions);
        }
    }

    protected void triggerHandlers(Class<?> eventType, Class<?> genericArgumentType, Object eventData, List<Exception> exceptions, InboxConfig inboxConfig) {

        for (EventTypeWithEventHandlerFactories handlerFactories : getHandlerFactories(eventType, genericArgumentType)) {
            for (IEventHandlerFactory handlerFactory : handlerFactories.eventHandlerFactories) {
                triggerHandler(handlerFactory, handlerFactories.eventType, eventData, exceptions, inboxConfig);
            }
        }

        //Implements generic argument inheritance. See IEventDataWithInheritableGenericArgument
        if (genericArgumentType != null && genericArgumentType.getSuperclass() != null) {
            publishToEventBus(eventType, genericArgumentType.getSuperclass(), eventData);
        }
    }

    protected void throwOriginalExceptions(Class<?> eventType, Class<?> genericArgumentType, List<Exception> exceptions) throws BaseException {
        if (exceptions.size() == 1) {
            throw new BaseException(exceptions.get(0));
        }

        throw new BaseException(String.format("More than one error has occurred while triggering the event: %s:[%s]", eventType, genericArgumentType));
    }

    protected void subscribeHandlers(List<? extends Class<? extends IEventHandler>> handlers) {
        for (Class<? extends IEventHandler> handler : handlers) {
            //TODO 需要替换java 实现
            Class<?>[]  interfaces =  handler.getInterfaces();

//                            for (Class<?> _interface : interfaces)
//                            {
//                                if (!IEventHandler.class.isAssignableFrom(_interface))
//                                {
//                                    continue;
//                                }
//
//                                var genericArgs = _interface.GetGenericArguments();
//                                if (genericArgs.Length == 1)
//                                {
//                                    subscribe(genericArgs[0], new SpringBeanEventHandlerFactory(beanFactory, handler));
//                                }
//                            }
        }
    }

    protected abstract List<EventTypeWithEventHandlerFactories> getHandlerFactories(Class<?> eventType, Class<?> genericArgumentType);

    protected void triggerHandler(IEventHandlerFactory asyncHandlerFactory, Class<?> eventType,
                                  Object eventData, List<Exception> exceptions, InboxConfig inboxConfig) {
        IEventHandlerDisposeWrapper eventHandlerWrapper = asyncHandlerFactory.getHandler();
        try {
            Class<?> handlerType = eventHandlerWrapper.getEventHandler().getClass();
            if (inboxConfig != null && inboxConfig.getHandlerSelector() != null &&
                    !inboxConfig.getHandlerSelector().apply(handlerType)) {
                return;
            }
            IDisposable disposable = currentTenant.change(getEventDataTenantId(eventData), null);
            eventHandlerInvoker.invoke(eventHandlerWrapper.getEventHandler(), eventData, eventType);
            disposable.dispose();
        } catch (Exception ex) {
            exceptions.add(ex);
        }
        eventHandlerWrapper.dispose();
    }

    protected ID getEventDataTenantId(Object eventData) {
        if (eventData instanceof IMultiTenant) {
            return ((IMultiTenant) eventData).getTenantId();
        } else if (eventData instanceof IEventDataMayHaveTenantId && ((IEventDataMayHaveTenantId) eventData).isMultiTenant()) {
            return ((IEventDataMayHaveTenantId) eventData).getTenantId();
        } else {
            return currentTenant.getId();
        }
    }

    protected static class EventTypeWithEventHandlerFactories {
        public Class<?> eventType;

        public List<IEventHandlerFactory> eventHandlerFactories;

        public EventTypeWithEventHandlerFactories(Class<?> eventType, List<IEventHandlerFactory> eventHandlerFactories) {
            this.eventType = eventType;
            this.eventHandlerFactories = eventHandlerFactories;
        }
    }
}
