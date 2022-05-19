package com.ueueo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 联合ID
 *
 * @author Lee
 * @date 2022-05-19 11:05
 */
public class UnionID extends ID {
    private Object[] keys;

    protected UnionID() {
        super();
    }

    @Override
    public boolean isStringValue() {
        return false;
    }

    @Override
    public boolean isNumberValue() {
        return false;
    }

    public static UnionID valueOf(@NonNull Object... keys) {
        Assert.notEmpty(keys, "keys must not empty!");
        UnionID unionId = new UnionID();
        unionId.keys = keys;
        unionId.stringValue = StringUtils.join(keys, ":");
        return unionId;
    }

}
