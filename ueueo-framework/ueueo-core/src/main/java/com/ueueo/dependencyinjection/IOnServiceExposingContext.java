package com.ueueo.dependencyinjection;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-18 10:26
 */
public interface IOnServiceExposingContext {
    Type getImplementationType();

    List<Type> getExposedTypes();
}
