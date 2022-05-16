package com.ueueo.settings;

import com.ueueo.AbpException;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 20:32
 */
public interface ISettingDefinitionManager {
    SettingDefinition get(String name)  throws AbpException;

    List<SettingDefinition> getAll();

    SettingDefinition getOrNull(String name);
}
