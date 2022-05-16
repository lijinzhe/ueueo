package com.ueueo.eventbus;

/**
 * @author Lee
 * @date 2022-05-16 09:16
 */
public interface IEventNameProvider {
    String getName(Class<?> eventType);
}
