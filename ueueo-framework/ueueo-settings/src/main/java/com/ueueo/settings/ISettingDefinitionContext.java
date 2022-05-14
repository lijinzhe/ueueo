package com.ueueo.settings;

import java.util.Collection;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 20:28
 */
public interface ISettingDefinitionContext {

    SettingDefinition getOrNull(String name);

    Collection<SettingDefinition> getAll();

    void add(SettingDefinition... definitions);

}
