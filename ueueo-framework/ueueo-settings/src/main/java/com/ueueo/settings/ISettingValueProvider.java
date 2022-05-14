package com.ueueo.settings;

import java.util.Collection;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 20:36
 */
public interface ISettingValueProvider {
    String name();

    String getOrNull(SettingDefinition setting);

    Collection<SettingValue> getAll(Collection<SettingDefinition> settings);
}
