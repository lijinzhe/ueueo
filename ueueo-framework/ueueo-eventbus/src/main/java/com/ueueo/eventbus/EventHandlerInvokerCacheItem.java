package com.ueueo.eventbus;

import lombok.Data;

@Data
public class EventHandlerInvokerCacheItem {

    private IEventHandlerMethodExecutor local;

    private IEventHandlerMethodExecutor distributed;
}
