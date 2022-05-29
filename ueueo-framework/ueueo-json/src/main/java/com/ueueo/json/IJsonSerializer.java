package com.ueueo.json;

/**
 * @author Lee
 * @date 2022-05-29 14:27
 */
public interface IJsonSerializer {
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
