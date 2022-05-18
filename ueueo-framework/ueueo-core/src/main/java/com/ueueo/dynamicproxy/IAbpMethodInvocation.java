package com.ueueo.dynamicproxy;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-18 10:23
 */
public interface IAbpMethodInvocation {

    Object[] getArguments();

    Map<String, Object> getArgumentsDictionary();

    Type[] getGenericArguments();

    Object getTargetObject();

    Method getMethod();

    Object getReturnValue();

    void setReturnValue(Object value);

    void proceed();
}
