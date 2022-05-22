package com.ueueo.data;

import com.ueueo.IDisposable;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 16:29
 */
public interface IDataFilter<TFilter> {

    IDisposable enable();

    IDisposable disable();

    boolean isEnabled();
}
