package com.ueueo.core.logging;

/**
 * @author Lee
 * @date 2022-05-14 13:07
 */
public interface IInitLoggerFactory <T> {
    IInitLogger<T> create(Class<T> t);
}
