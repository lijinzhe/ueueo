package com.ueueo.settings;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:36
 */
public interface ISettingValueProvider {
    String getName();

    String getOrNull(@NonNull SettingDefinition setting);

    List<SettingValue> getAll(@NonNull List<SettingDefinition> settings);

}
