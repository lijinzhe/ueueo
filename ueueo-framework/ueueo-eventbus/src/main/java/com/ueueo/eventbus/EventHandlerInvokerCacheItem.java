package com.ueueo.eventbus;

public class EventHandlerInvokerCacheItem
{
    public IEventHandlerMethodExecutor Local;// { get; set; }

    public IEventHandlerMethodExecutor Distributed;// { get; set; }
}
