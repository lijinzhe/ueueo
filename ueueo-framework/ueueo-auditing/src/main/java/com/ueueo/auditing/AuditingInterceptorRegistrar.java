package com.ueueo.auditing;

import com.ueueo.dependencyinjection.IOnServiceRegistredContext;
import com.ueueo.dynamicproxy.DynamicProxyIgnoreTypes;

import java.util.Arrays;

/**
 * @author Lee
 * @date 2022-05-26 17:56
 */
public class AuditingInterceptorRegistrar {

    public static void registerIfNeeded(IOnServiceRegistredContext context) {
        if (shouldIntercept(context.getImplementationType())) {
            context.getInterceptors().add(AuditingInterceptor.class);
        }
    }

    private static boolean shouldIntercept(Class<?> type) {
        if (DynamicProxyIgnoreTypes.contains(type, true)) {
            return false;
        }
        if (Boolean.TRUE.equals(shouldAuditTypeByDefaultOrNull(type))) {
            return true;
        }
        if (Arrays.stream(type.getMethods()).anyMatch(m -> m.isAnnotationPresent(Audited.class))) {
            return true;
        }
        return false;
    }

    //TODO: Move to a better place
    public static Boolean shouldAuditTypeByDefaultOrNull(Class<?> type) {
        //TODO: In an inheritance chain, it would be better to check the attributes on the top class first.
        if (type.isAnnotationPresent(Audited.class)) {
            return true;
        }
        if (type.isAnnotationPresent(DisableAuditing.class)) {
            return false;
        }
        if (type.isAssignableFrom(IAuditingEnabled.class)) {
            return true;
        }
        return null;
    }
}
