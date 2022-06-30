package com.ueueo.eventbus;

import com.ueueo.disposable.IDisposable;

/**
 * @author Lee
 * @date 2022-05-30 21:47
 */
public interface IEventHandlerDisposeWrapper extends IDisposable {
    IEventHandler getEventHandler();
}
