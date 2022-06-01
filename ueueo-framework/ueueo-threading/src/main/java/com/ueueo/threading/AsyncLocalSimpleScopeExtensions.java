package com.ueueo.threading;

import com.ueueo.DisposeAction;
import com.ueueo.IDisposable;

public class AsyncLocalSimpleScopeExtensions {

    public static <T> IDisposable setScoped(ThreadLocal<T> asyncLocal, T value) {
        T previousValue = asyncLocal.get();
        asyncLocal.set(value);
        return new DisposeAction(() -> asyncLocal.set(previousValue));
    }
}
