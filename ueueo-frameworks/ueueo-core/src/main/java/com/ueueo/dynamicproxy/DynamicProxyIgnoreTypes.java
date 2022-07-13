package com.ueueo.dynamicproxy;

import java.util.HashSet;
import java.util.Set;

/**
 * Castle's dynamic proxy class feature will have performance issues for some components, such as the controller of Asp net core MVC.
 * For related discussions, see: https://github.com/castleproject/Core/issues/486 https://github.com/abpframework/abp/issues/3180
 * The Abp framework may enable interceptors for certain components (UOW, Auditing, Authorization, etc.), which requires dynamic proxy classes, but will cause application performance to decline.
 * We need to use other methods for the controller to implement interception, such as middleware or MVC / Page filters.
 * So we provide some ignored types to avoid enabling dynamic proxy classes.
 * By default it is empty. When you use middleware or filters for these components in your application, you can add these types to the list.
 *
 * @author Lee
 * @date 2022-05-18 11:23
 */
public class DynamicProxyIgnoreTypes {
    private static final Set<Class<?>> IGNORED_TYPES = new HashSet<>();

    public static void add(Class<?> t) {
        synchronized (IGNORED_TYPES) {
            IGNORED_TYPES.add(t);
        }
    }

    /**
     *
     * @param type
     * @param includeDerivedTypes default true
     * @return
     */
    public static boolean contains(Class<?> type, Boolean includeDerivedTypes) {
        synchronized (IGNORED_TYPES) {
            return includeDerivedTypes == null || includeDerivedTypes
                    ? IGNORED_TYPES.stream().anyMatch(t -> t.isAssignableFrom(type))
                    : IGNORED_TYPES.contains(type);
        }
    }
}
