package com.ueueo.dependencyinjection;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-18 10:31
 */
@Data
public class ObjectAccessor<T> implements IObjectAccessor<T> {

    private T value;

    public ObjectAccessor() {
    }

    public ObjectAccessor(T value) {
        this.value = value;
    }
}
