package com.ueueo.threading;

import com.ueueo.disposable.DisposeAction;
import com.ueueo.disposable.IDisposable;

public class AsyncLocalSimpleScopeExtensions {

    public static <T> IDisposable setScoped(ThreadLocal<T> asyncLocal, T value) {
        T previousValue = asyncLocal.get();
        asyncLocal.set(value);
        return new DisposeAction(() -> asyncLocal.set(previousValue));
    }
}
