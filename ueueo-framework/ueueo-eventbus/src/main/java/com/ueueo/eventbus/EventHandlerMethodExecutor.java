package com.ueueo.eventbus;

import com.ueueo.eventbus.distributed.IDistributedEventHandler;
import com.ueueo.eventbus.local.ILocalEventHandler;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-06-14 10:28
 */
public class EventHandlerMethodExecutor {

    public static class LocalEventHandlerMethodExecutor implements IEventHandlerMethodExecutor {

        @Override
        public void execute(IEventHandler target, Object parameters) {
            ((ILocalEventHandler) target).handleEvent(parameters);
        }
    }

    public static class DistributedEventHandlerMethodExecutor implements IEventHandlerMethodExecutor {
        @Override
        public void execute(IEventHandler target, Object parameters) {
            ((IDistributedEventHandler) target).handleEvent(parameters);
        }
    }

}
