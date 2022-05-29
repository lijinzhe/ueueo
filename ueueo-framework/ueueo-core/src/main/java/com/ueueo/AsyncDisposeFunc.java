package com.ueueo;

import com.ueueo.threading.IAsyncDisposable;

import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * This class can be used to provide an action when
 * DisposeAsync method is called.
 *
 * @author Lee
 * @date 2022-05-29 13:23
 */
public class AsyncDisposeFunc implements IAsyncDisposable {

    private Supplier<Future<?>> func;

    public AsyncDisposeFunc(Supplier<Future<?>> func) {
        this.func = func;
    }

    @Override
    public Future<?> disposeAsync() {
        return func.get();
    }
}
