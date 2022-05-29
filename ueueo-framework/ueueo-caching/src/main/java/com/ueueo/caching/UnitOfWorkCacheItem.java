package com.ueueo.caching;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-29 14:23
 */
@Data
public class UnitOfWorkCacheItem<TValue extends Class<?>> {
    public boolean isRemoved;

    public TValue value;

    public UnitOfWorkCacheItem() {

    }

    public UnitOfWorkCacheItem(TValue value) {
        this.value = value;
    }

    public UnitOfWorkCacheItem(TValue value, boolean isRemoved) {
        this.value = value;
        this.isRemoved = isRemoved;
    }

    public UnitOfWorkCacheItem<TValue> SetValue(TValue value) {
        this.value = value;
        this.isRemoved = false;
        return this;
    }

    public UnitOfWorkCacheItem<TValue> RemoveValue() {
        this.value = null;
        this.isRemoved = true;
        return this;
    }
}
