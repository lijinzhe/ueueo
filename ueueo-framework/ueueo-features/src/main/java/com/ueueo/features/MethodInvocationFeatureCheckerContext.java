package com.ueueo.features;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public class MethodInvocationFeatureCheckerContext {
    @Getter
    private final Method method;

    public MethodInvocationFeatureCheckerContext(Method method) {
        this.method = method;
    }
}
