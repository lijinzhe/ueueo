package com.ueueo.threading;

/**
 * @author Lee
 * @date 2022-05-26 16:56
 */
public interface IAmbientDataContext {
    void setData(String key, Object value);

    Object getData(String key);
}
