package com.ueueo.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * Spring Assert的扩展
 *
 * @author Lee
 * @date 2022-06-29 09:35
 */
public class Check extends Assert {
    public static void notNullOrWhiteSpace(String text, String message) {
        isTrue(StringUtils.isNotBlank(text), message);
    }
}
