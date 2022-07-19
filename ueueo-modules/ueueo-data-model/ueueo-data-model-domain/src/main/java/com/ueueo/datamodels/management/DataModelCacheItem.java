package com.ueueo.datamodels.management;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lee
 * @date 2022-07-15 09:35
 */
@Data
public class DataModelCacheItem {

    private static final String CACHE_KEY_FORMAT = "pn:%s,pk:%s,n:%s";

    private static final Pattern PATTERN = Pattern.compile("pn:[\\S]*,pk:[\\S]*,n:([\\S]*)");

    private DataModel value;

    public DataModelCacheItem() {

    }

    public DataModelCacheItem(DataModel value) {
        this.value = value;
    }

    public static String calculateCacheKey(String name, String providerName, String providerKey) {
        return String.format(CACHE_KEY_FORMAT, providerName, providerKey, name);
    }

    public static String getDataModelNameFormCacheKeyOrNull(String cacheKey) {
        String result = null;
        Matcher matcher = PATTERN.matcher(cacheKey);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }
}
