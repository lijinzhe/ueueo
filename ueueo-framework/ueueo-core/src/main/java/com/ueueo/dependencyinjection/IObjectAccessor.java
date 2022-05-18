package com.ueueo.dependencyinjection;

import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-18 10:30
 */
public interface IObjectAccessor<T> {
    @Nullable
    T getValue();
}
