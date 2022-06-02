package com.ueueo.settings;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:28
 */
public interface ISettingDefinitionContext {

    SettingDefinition getOrNull(String name);

    List<SettingDefinition> getAll();

    void add(SettingDefinition... definitions);

}
