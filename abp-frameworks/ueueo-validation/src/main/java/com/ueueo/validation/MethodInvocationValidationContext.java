package com.ueueo.validation;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author Lee
 * @date 2022-05-29 17:13
 */
@Getter
public class MethodInvocationValidationContext extends AbpValidationResult {
    private Object targetObject;

    private Method method;

    private Object[] parameterValues;

    private Class<?>[] parameters;

    public MethodInvocationValidationContext(Object targetObject, Method method, Object[] parameterValues, Class<?>[] parameters) {
        this.targetObject = targetObject;
        this.method = method;
        this.parameterValues = parameterValues;
        this.parameters = parameters;
    }
}
