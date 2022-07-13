package com.ueueo;

import lombok.Data;

import java.io.Serializable;

/**
 * Can be used to store Name/Value (or Key/Value) pairs.
 *
 * @author Lee
 * @date 2021-08-18 20:16
 */
@Data
public class NameValue<V> implements Serializable {

    /** Name */
    private String name;

    /** Value. */
    private V value;

    public NameValue() {
    }

    public NameValue(String name, V value) {
        this.name = name;
        this.value = value;
    }

}
