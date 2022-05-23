package com.ueueo.auditing;

/**
 * @author Lee
 * @date 2022-05-18 15:22
 */
public enum EntityChangeType {
    /** 创建 */
    Created(0),
    /** 修改 */
    Updated(1),
    /** 删除 */
    Deleted(2);

    private int value;

    EntityChangeType(int value) {
        this.value = value;
    }
}
