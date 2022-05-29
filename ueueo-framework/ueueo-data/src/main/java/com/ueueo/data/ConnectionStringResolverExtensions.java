package com.ueueo.data;

/**
 * @author Lee
 * @date 2022-05-29 15:01
 */
public class ConnectionStringResolverExtensions {

    public static String resolve(IConnectionStringResolver resolver) {
        return resolve(resolver, resolver.getClass());
    }

    public static String resolve(IConnectionStringResolver resolver, Class<?> type) {
        ConnectionStringName annotation = type.getAnnotation(ConnectionStringName.class);
        if (annotation == null) {
            return type.getName();
        } else {
            return annotation.name();
        }
    }

}
