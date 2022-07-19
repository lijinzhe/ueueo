package com.ueueo.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Spring Assert的扩展
 *
 * @author Lee
 * @date 2022-06-29 09:35
 */
public class Check {

    public static <T> T notNull(T value, @NonNull String parameterName) {
        return notNull(value, parameterName, "%s can not be null!");
    }

    public static <T> T notNull(T value, @NonNull String parameterName, @NonNull String message) {
        Assert.notNull(value, String.format(message, parameterName));
        return value;
    }

    public static String notNullOrWhiteSpace(String value, @NonNull String parameterName) {
        Assert.isTrue(StringUtils.isNotBlank(value), String.format("%s can not be null, empty or white space!", parameterName));
        return value;

    }

    public static String notNullOrWhiteSpace(String value, @NonNull String parameterName, Integer maxLength, Integer minLength) {
        Assert.isTrue(StringUtils.isNotBlank(value), String.format("%s can not be null, empty or white space!", parameterName));

        if (maxLength != null && maxLength > 0) {
            Assert.isTrue(value.length() <= maxLength, String.format("%s length must be equal to or lower than %s", parameterName, maxLength));
        }

        if (minLength != null && minLength > 0) {
            Assert.isTrue(value.length() >= minLength, String.format("%s length must be equal to or bigger than %s!", parameterName, minLength));
        }

        return value;
    }

    public static String notNullOrEmpty(String value, @NonNull String parameterName) {
        Assert.isTrue(StringUtils.isNotEmpty(value), String.format("%s can not be null or empty!", parameterName));
        return value;

    }

    public static String notNullOrEmpty(String value, @NonNull String parameterName, Integer maxLength, Integer minLength) {
        Assert.isTrue(StringUtils.isNotEmpty(value), String.format("%s can not be null or empty!", parameterName));

        if (maxLength != null && maxLength > 0) {
            Assert.isTrue(value.length() <= maxLength, String.format("%s length must be equal to or lower than %s", parameterName, maxLength));
        }

        if (minLength != null && minLength > 0) {
            Assert.isTrue(value.length() >= minLength, String.format("%s length must be equal to or bigger than %s!", parameterName, minLength));
        }

        return value;
    }

    public static <T> T notNullOrEmpty(T value, @NonNull String parameterName) {
        Assert.notNull(value, parameterName + " can not be null or empty!");
        if (value instanceof Collection) {
            Assert.notEmpty((Collection<?>) value, parameterName + " can not be null or empty!");
        } else if (value instanceof Map) {
            Assert.notEmpty((Map<?, ?>) value, parameterName + " can not be null or empty!");
        }else if (value instanceof Object[]) {
            Assert.notEmpty((Object[]) value, parameterName + " can not be null or empty!");
        }
        return value;
    }
}
