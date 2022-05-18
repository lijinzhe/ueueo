package com.ueueo.dynamicproxy;

/**
 * @author Lee
 * @date 2022-05-18 10:22
 */
public interface IAbpInterceptor {
    void intercept(IAbpMethodInvocation invocation);

}
