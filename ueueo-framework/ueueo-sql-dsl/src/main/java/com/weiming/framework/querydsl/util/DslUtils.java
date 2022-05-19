package com.weiming.framework.querydsl.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DslUtils {
    public static final String BACK_QUOTE = "`";
    public static final String DOT = ".";
    public static final String SINGLE_QUOTE = "'";
//    public static final String FIELD_START = "{";
//    public static final String FIELD_END = "}";


    public static String quoteValue(String value) {
        if (value != null) {
            return isField(value) ?
                quoteField(value) : singleQuoteIfMissing(value);
        }
        return null;
    }

    public static boolean isField(String value) {
        return (StringUtils.startsWith(value, BACK_QUOTE) && StringUtils.endsWith(value, BACK_QUOTE));
    }

    public static String removeStartEnd(String str, String start, String end) {
        String temp = StringUtils.removeStart(str, start);
        temp = StringUtils.removeEnd(temp, end);
        return temp;
    }

    public static String quoteField(String field) {
        String[] split = field.split("\\" + DslUtils.DOT);
        if (split.length == 2) {
            return Arrays.stream(split).map(DslUtils::backQuoteIfMissing).collect(Collectors.joining(DslUtils.DOT));
        }
        if (split.length > 2) {
            throw new IllegalArgumentException("field should not have more than one '.'");
        } else {
            return backQuoteIfMissing(field);
        }
    }

    public static String singleQuoteIfMissing(String str) {
        return StringUtils.wrapIfMissing(str, DslUtils.SINGLE_QUOTE);
    }

    public static String backQuoteIfMissing(String str) {
        return StringUtils.wrapIfMissing(str, DslUtils.BACK_QUOTE);
    }

    public static <T> List<T> unmodifiableListOrEmpty(List<? extends T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        } else {
            return Collections.unmodifiableList(list);
        }
    }
}
