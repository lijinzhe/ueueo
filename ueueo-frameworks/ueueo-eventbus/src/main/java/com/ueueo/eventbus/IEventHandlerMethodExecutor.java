package com.ueueo.eventbus;

/**
 * @author Lee
 * @date 2022-05-30 22:04
 */
public interface IEventHandlerMethodExecutor {
    void execute(IEventHandler target, Object parameters);
}
