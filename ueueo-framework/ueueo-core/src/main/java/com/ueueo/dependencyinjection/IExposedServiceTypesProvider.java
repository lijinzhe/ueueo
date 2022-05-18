package com.ueueo.dependencyinjection;

import java.lang.reflect.Type;

/**
 * @author Lee
 * @date 2022-05-18 10:39
 */
public interface IExposedServiceTypesProvider {
    Type[] getExposedServiceTypes(Type targetType);
}
