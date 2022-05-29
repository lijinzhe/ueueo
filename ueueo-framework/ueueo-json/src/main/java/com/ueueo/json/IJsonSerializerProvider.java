package com.ueueo.json;

import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-29 14:29
 */
public interface IJsonSerializerProvider {

    boolean canHandle(@Nullable Class<?> type);

    /**
     * @param obj
     * @param camelCase default true
     * @param indented  default false
     *
     * @return
     */
    String serialize(Object obj, boolean camelCase, boolean indented);

    <T> T deserialize(Class<T> type, String jsonString, boolean camelCase);
}
