package com.ueueo.auditing;

/**
 * @author Lee
 * @date 2022-05-18 15:22
 */
public enum EntityChangeType {
    Created(0),

    Updated(1),

    Deleted(2);

    private int value;

    EntityChangeType(int value) {
        this.value = value;
    }
}
