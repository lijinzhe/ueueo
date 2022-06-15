package com.ueueo.eventbus;

import com.ueueo.ID;
import com.ueueo.IDisposable;
import com.ueueo.eventbus.distributed.InboxConfig;
import com.ueueo.eventbus.local.ILocalEventHandler;
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

    protected ICurrentTenant CurrentTenant;

    protected IUnitOfWorkManager UnitOfWorkManager;

    protected IEventHandlerInvoker EventHandlerInvoker;

    protected EventBusBase(
            BeanFactory beanFactory,
            ICurrentTenant currentTenant,
            IUnitOfWorkManager unitOfWorkManager,
            IEventHandlerInvoker eventHandlerInvoker) {
        this.beanFactory = beanFactory;
        CurrentTenant = currentTenant;
        UnitOfWorkManager = unitOfWorkManager;
        EventHandlerInvoker = eventHandlerInvoker;
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
    public abstract IDisposable subscribe(Class<?> eventType, IEventHandlerFactory factory);

    @Override
    public abstract void unsubscribe(Class<?> eventType, Consumer<Object> action);

    @Override
    public abstract void unsubscribe(Class<?> eventType, IEventHandler handler);

    @Override
    public void unsubscribe(Class<?> eventType, ILocalEventHandler handler) {
        unsubscribe(eventType, handler);
    }

    @Override
    public abstract void unsubscribe(Class<?> eventType, IEventHandlerFactory factory);

    @Override
    public abstract void unsubscribeAll(Class<?> eventType);

    @Override
    public void publish(Object eventData, Boolean onUnitOfWorkComplete) {
        publish(eventData.getClass(), eventData, onUnitOfWorkComplete);
    }

    @Override
    public void publish(Class<?> eventType, Object eventData, Boolean onUnitOfWorkComplete) {
        if (onUnitOfWorkComplete && UnitOfWorkManager.getCurrent() != null) {
            addToUnitOfWork(
                    UnitOfWorkManager.getCurrent(),
                    new UnitOfWorkEventRecord(eventType, eventData, EventOrderGenerator.getNext(), null)
            );
            return;
        }

        publishToEventBus(eventType, eventData);
    }

    protected abstract void publishToEventBus(Class<?> eventType, Object eventData);

    protected abstract void addToUnitOfWork(IUnitOfWork unitOfWork, UnitOfWorkEventRecord eventRecord);

    public void triggerHandlers(Class<?> eventType, Object eventData) throws Exception {
        List<Exception> exceptions = new ArrayList<>();

        triggerHandlers(eventType, eventData, exceptions, null);

        if (!exceptions.isEmpty()) {
            throwOriginalExceptions(eventType, exceptions);
        }
    }

    protected void triggerHandlers(Class<?> eventType, Object eventData, List<Exception> exceptions, InboxConfig inboxConfig) {

        for (EventTypeWithEventHandlerFactories handlerFactories : getHandlerFactories(eventType)) {
            for (IEventHandlerFactory handlerFactory : handlerFactories.eventHandlerFactories) {
                triggerHandler(handlerFactory, handlerFactories.eventType, eventData, exceptions, inboxConfig);
            }
        }

        //Implements generic argument inheritance. See IEventDataWithInheritableGenericArgument
        //TODO 注释掉了泛型的解析
        //        if (eventType.GetTypeInfo().IsGenericType &&
        //            eventType.GetGenericArguments().Length == 1 &&
        //                IEventDataWithInheritableGenericArgument.class.isAssignableFrom(eventType))
        //        {
        //            var genericArg = eventType.GetGenericArguments()[0];
        //            var baseArg = genericArg.GetTypeInfo().BaseType;
        //            if (baseArg != null)
        //            {
        //                Class<?> baseEventType = eventType.GetGenericTypeDefinition().MakeGenericType(baseArg);
        //                Object[] constructorArgs = ((IEventDataWithInheritableGenericArgument)eventData).getConstructorArgs();
        //                Object baseEventData = Activator.CreateInstance(baseEventType, constructorArgs);
        //                PublishToEventBusAsync(baseEventType, baseEventData);
        //            }
        //        }
    }

    protected void throwOriginalExceptions(Class<?> eventType, List<Exception> exceptions) throws Exception {
        if (exceptions.size() == 1) {
            throw exceptions.get(0);
        }

        throw new Exception(
                "More than one error has occurred while triggering the event: " + eventType
                //            exceptions
        );
    }

    protected void subscribeHandlers(List<? extends Class<? extends IEventHandler>> handlers) {
        for (Class<? extends IEventHandler> handler : handlers) {
            //TODO 需要替换java 实现
            //                var interfaces = handler.GetInterfaces();
            //                for (var @interface in interfaces)
            //                {
            //                    if (!typeof(IEventHandler).GetTypeInfo().IsAssignableFrom(@interface))
            //                    {
            //                        continue;
            //                    }
            //
            //                    var genericArgs = @interface.GetGenericArguments();
            //                    if (genericArgs.Length == 1)
            //                    {
            //                        subscribe(genericArgs[0], new IocEventHandlerFactory(beanFactory, handler));
            //                    }
            //                }
        }
    }

    protected abstract List<EventTypeWithEventHandlerFactories> getHandlerFactories(Class<?> eventType);

    protected void triggerHandler(IEventHandlerFactory asyncHandlerFactory, Class<?> eventType,
                                  Object eventData, List<Exception> exceptions, InboxConfig inboxConfig) {
        IEventHandlerDisposeWrapper eventHandlerWrapper = asyncHandlerFactory.getHandler();
        try {
            Class<?> handlerType = eventHandlerWrapper.getEventHandler().getClass();
            if (inboxConfig != null && inboxConfig.getHandlerSelector() != null &&
                    !inboxConfig.getHandlerSelector().apply(handlerType)) {
                return;
            }
            IDisposable disposable = CurrentTenant.change(getEventDataTenantId(eventData), null);
            EventHandlerInvoker.invoke(eventHandlerWrapper.getEventHandler(), eventData, eventType);
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
            return CurrentTenant.getId();
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
