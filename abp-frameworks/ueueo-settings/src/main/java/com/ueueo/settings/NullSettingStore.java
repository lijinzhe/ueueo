package com.ueueo.settings;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-18 20:39
 */
public class NullSettingStore implements ISettingStore {

    @Override
    public String getOrNull(String name, String providerName, String providerKey) {
        return null;
    }

    @Override
    public List<SettingValue> getAll(List<String> names, String providerName, String providerKey) {
        return names.stream().map(s -> new SettingValue(s, null)).collect(Collectors.toList());
    }
}
