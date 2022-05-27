package com.ueueo.dependencyinjection;

import org.aopalliance.intercept.MethodInterceptor;

import java.util.List;

/**
 * @author Lee
 * @date 2022-05-18 10:26
 */
public interface IOnServiceRegistredContext {
    List<Class<? extends MethodInterceptor>> getInterceptors();

    Class<?> getImplementationType();
}
