package com.ueueo.dynamicproxy;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

/**
 * @author Lee
 * @date 2022-05-29 12:22
 */
public final class ProxyHelper {

    /**
     * 获取代理对象的 目标对象
     * Returns dynamic proxy target object if this is a proxied object,
     * otherwise returns the given object.
     *
     * @param proxy 代理对象
     *
     * @return dynamic proxy target object if this is a proxied object, otherwise returns the given object.
     *
     * @throws Exception
     */
    public static Object unProxy(Object proxy) throws RuntimeException {
        if (!AopUtils.isAopProxy(proxy)) {
            //不是代理对象，直接返回原对象
            return proxy;
        }

        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else {
            return getCglibProxyTargetObject(proxy);
        }
    }

    /**
     * 获取代理对象的原始类型
     *
     * @param proxy proxy 代理对象
     *
     * @return
     */
    public static Class<?> getUnProxiedType(@NonNull Object proxy) {
        return AopUtils.getTargetClass(proxy);
    }

    /**
     * 获取当前类的被代理对象
     *
     * @param type
     * @param <T>
     *
     * @return
     */
    public <T> T getCurrentProxy(Class<T> type) {
        return (T) AopContext.currentProxy();
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws RuntimeException {
        try {
            Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
            h.setAccessible(true);
            Object dynamicAdvisedInterceptor = h.get(proxy);

            Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws RuntimeException {
        try {
            Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            AopProxy aopProxy = (AopProxy) h.get(proxy);

            Field advised = aopProxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);

            return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
