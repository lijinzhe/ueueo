package com.ueueo.data;

import com.ueueo.disposable.IDisposable;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 16:29
 */
public interface IDataFilter<TFilter> {

    IDisposable enable(Class<TFilter> type);

    IDisposable disable(Class<TFilter> type);

    boolean isEnabled(Class<TFilter> type);
}
