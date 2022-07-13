package com.ueueo.threading;

import java.util.concurrent.ConcurrentHashMap;

public class AsyncLocalAmbientDataContext implements IAmbientDataContext {

    private static final ConcurrentHashMap<String, ThreadLocal<Object>> ASYNC_LOCAL_DICTIONARY = new ConcurrentHashMap<>();

    @Override
    public void setData(String key, Object value) {
        ThreadLocal<Object> asyncLocal = ASYNC_LOCAL_DICTIONARY.computeIfAbsent(key, s -> new ThreadLocal<>());
        asyncLocal.set(value);
    }

    @Override
    public Object getData(String key) {
        ThreadLocal<Object> asyncLocal = ASYNC_LOCAL_DICTIONARY.computeIfAbsent(key, s -> new ThreadLocal<>());
        return asyncLocal.get();
    }
}
