package com.ueueo.logging;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lee
 * @date 2022-05-14 13:19
 */
public class DefaultInitLoggerFactory<T> implements IInitLoggerFactory<T> {

    private Map<String, IInitLogger<T>> cache = new HashMap<>();

    @Override
    public IInitLogger<T> create(Class<T> t) {
        IInitLogger<T> logger = cache.get(t.getName());
        if (logger == null) {
            cache.put(t.getName(), new DefaultInitLogger<>());
        }
        return logger;
    }
}
