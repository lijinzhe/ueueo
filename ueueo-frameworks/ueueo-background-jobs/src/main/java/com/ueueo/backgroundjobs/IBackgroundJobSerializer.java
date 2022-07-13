package com.ueueo.backgroundjobs;

/**
 * @author Lee
 * @date 2022-05-29 18:04
 */
public interface IBackgroundJobSerializer {
    String serialize(Object obj);

    <T> T deserialize(String value, Class<T> type);
}
