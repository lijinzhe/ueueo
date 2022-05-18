package com.ueueo.dependencyinjection;

/**
 * @author Lee
 * @date 2022-05-18 10:31
 */
public class ObjectAccessor<T> implements IObjectAccessor<T> {

    private T value;

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ObjectAccessor() {
    }

    public ObjectAccessor(T value) {
        this.value = value;
    }
}
