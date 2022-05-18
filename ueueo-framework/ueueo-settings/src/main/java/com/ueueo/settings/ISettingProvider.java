package com.ueueo.settings;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:33
 */
public interface ISettingProvider {

    String getOrNull(String name);

    List<SettingValue> getAll(@NonNull List<String> names);

    List<SettingValue> getAll();

}
