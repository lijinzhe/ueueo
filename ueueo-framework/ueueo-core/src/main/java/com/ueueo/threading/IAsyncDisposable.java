package com.ueueo.threading;

import java.util.concurrent.Future;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-29 13:25
 */
public interface IAsyncDisposable {
    Future<?> disposeAsync();
}
