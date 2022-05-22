package com.ueueo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * 联合ID
 *
 * Union ID
 *
 * @author Lee
 * @date 2022-05-19 11:05
 */
public class UID extends ID {
    private Object[] keys;

    protected UID() {
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

    public static UID valueOf(@NonNull Object... keys) {
        Assert.notEmpty(keys, "keys must not empty!");
        UID unionId = new UID();
        unionId.keys = keys;
        unionId.stringValue = StringUtils.join(keys, ":");
        return unionId;
    }

}
