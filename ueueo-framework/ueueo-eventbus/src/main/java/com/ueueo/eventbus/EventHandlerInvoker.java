package com.ueueo.eventbus;

import com.ueueo.exception.BaseException;
import com.ueueo.eventbus.distributed.IDistributedEventHandler;
import com.ueueo.eventbus.local.ILocalEventHandler;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventHandlerInvoker implements IEventHandlerInvoker {

    private ConcurrentHashMap<String, EventHandlerInvokerCacheItem> cache;

    public EventHandlerInvoker() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void invoke(IEventHandler eventHandler, Object eventData, Class<?> eventType) {
        EventHandlerInvokerCacheItem cacheItem = cache.computeIfAbsent(String.format("%s-%s",
                eventHandler.getClass().getName(), eventType.getName()), key ->
        {
            EventHandlerInvokerCacheItem item = new EventHandlerInvokerCacheItem();
            if (eventHandler instanceof ILocalEventHandler) {
                item.setLocal(new EventHandlerMethodExecutor.LocalEventHandlerMethodExecutor());
            }
            if (eventHandler instanceof IDistributedEventHandler) {
                item.setDistributed(new EventHandlerMethodExecutor.DistributedEventHandlerMethodExecutor());
            }
            return item;
        });

        if (cacheItem.getLocal() != null) {
            cacheItem.getLocal().execute(eventHandler, eventData);
        }

        if (cacheItem.getDistributed() != null) {
            cacheItem.getDistributed().execute(eventHandler, eventData);
        }

        if (cacheItem.getLocal() == null && cacheItem.getDistributed() == null) {
            throw new BaseException("The object instance is not an event handler. Object type: " + eventHandler.getClass().getName());
        }
    }
}
