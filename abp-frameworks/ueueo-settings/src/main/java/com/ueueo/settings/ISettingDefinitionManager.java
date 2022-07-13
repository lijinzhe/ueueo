package com.ueueo.settings;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:32
 */
public interface ISettingDefinitionManager {
    @NonNull
    SettingDefinition get(@NonNull String name);

    List<SettingDefinition> getAll();

    SettingDefinition getOrNull(String name);
}
