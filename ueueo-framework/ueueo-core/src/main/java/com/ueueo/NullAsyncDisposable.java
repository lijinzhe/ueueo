package com.ueueo;

import com.ueueo.threading.IAsyncDisposable;

import java.util.concurrent.Future;

/**
 *
 * @author Lee
 * @date 2022-05-29 13:34
 */
public class NullAsyncDisposable implements IAsyncDisposable {

    public static final NullAsyncDisposable INSTANCE = new NullAsyncDisposable();

    private NullAsyncDisposable() {

    }

    @Override
    public Future<?> disposeAsync() {
        return null;
    }

}
