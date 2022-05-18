package com.ueueo.dependencyinjection;

import com.ueueo.dynamicproxy.IAbpInterceptor;

import java.util.List;

/**
 * @author Lee
 * @date 2022-05-18 10:26
 */
public interface IOnServiceRegistredContext {
    List<Class<? extends IAbpInterceptor>> getInterceptors();

    Class<?> getImplementationType();
}
