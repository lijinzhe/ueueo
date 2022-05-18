package com.ueueo.settings;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:35
 */
public interface ISettingStore {

    String getOrNull(String name, String providerName, String providerKey);

    List<SettingValue> getAll(List<String> names, String providerName, String providerKey);

}
