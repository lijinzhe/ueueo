package com.ueueo.features;

import com.ueueo.dynamicproxy.DynamicProxyIgnoreTypes;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public class FeatureInterceptorRegistrar {
    public static void registerIfNeeded() {
        if (shouldIntercept(null)) {
//            context.getInterceptors().add(FeatureInterceptor.class);
        }
    }

    private static boolean shouldIntercept(Class<?> type) {
        return !DynamicProxyIgnoreTypes.contains(type, null) &&
                (type.isAnnotationPresent(RequiresFeatureAttribute.class) ||
                        anyMethodHasRequiresFeatureAttribute(type));
    }

    private static boolean anyMethodHasRequiresFeatureAttribute(Class<?> implementationType) {
        return Arrays.stream(implementationType
                .getMethods()).anyMatch(FeatureInterceptorRegistrar::hasRequiresFeatureAttribute);
    }

    private static boolean hasRequiresFeatureAttribute(Method methodInfo) {
        return methodInfo.isAnnotationPresent(RequiresFeatureAttribute.class);
    }
}
