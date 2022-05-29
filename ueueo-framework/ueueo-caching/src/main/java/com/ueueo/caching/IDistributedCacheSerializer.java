package com.ueueo.caching;

/**
 * @author Lee
 * @date 2022-05-29 14:21
 */
public interface IDistributedCacheSerializer {
    byte[] serialize(Object obj);

    <T> T deserialize(Class<T> type, byte[] bytes);
}
