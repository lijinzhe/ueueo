package com.ueueo.serialization;

public interface IObjectSerializer<T> {
    byte[] serialize(T obj);

    T deserialize(byte[] bytes);
}
