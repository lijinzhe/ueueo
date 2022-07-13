package com.ueueo.authorization;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author Lee
 * @date 2022-05-29 13:38
 */
@Getter
public class MethodInvocationAuthorizationContext {
    private Method method;

    public MethodInvocationAuthorizationContext(Method method) {
        this.method = method;
    }
}
