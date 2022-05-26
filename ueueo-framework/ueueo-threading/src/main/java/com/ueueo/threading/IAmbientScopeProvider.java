package com.ueueo.threading;

import com.ueueo.IDisposable;

/**
 * @author Lee
 * @date 2022-05-26 16:56
 */
public interface IAmbientScopeProvider<T> {

    T getValue(String contextKey);

    IDisposable beginScope(String contextKey, T value);
}
