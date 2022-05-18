package com.ueueo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-05-18 21:07
 */
@Slf4j
public class ID {
    private String stringValue;
    private Long numberValue;

    public String getStringValue() {
        return stringValue;
    }

    public Long getNumberValue() {
        return numberValue;
    }

    public boolean isStringValue() {
        return stringValue != null;
    }

    public boolean isNumberValue() {
        return numberValue != null;
    }

    public static ID valueOf(@NonNull String stringValue) {
        Assert.isTrue(StringUtils.isNoneBlank(stringValue), "value must not empty.");
        ID id = new ID();
        id.stringValue = stringValue;
        try {
            long v = Long.parseLong(stringValue);
            if (v > 0) {
                id.numberValue = v;
            }
        } catch (NumberFormatException e) {
            log.debug("ID string value number format exception!", e);
        }
        return id;
    }

    public static ID valueOf(long longValue) {
        Assert.isTrue(longValue > 0, "value must > 0");
        ID id = new ID();
        id.numberValue = longValue;
        id.stringValue = String.valueOf(longValue);
        return id;
    }

    public static ID valueOf(int intValue) {
        Assert.isTrue(intValue > 0, "value must > 0");
        ID id = new ID();
        id.numberValue = (long) intValue;
        id.stringValue = String.valueOf(intValue);
        return id;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
