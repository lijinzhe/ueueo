package com.ueueo.settings;


import com.ueueo.core.AbpException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 21:04
 */
public class SettingDefinitionManager implements ISettingDefinitionManager {

    protected Map<String, SettingDefinition> settingDefinitions;

    public SettingDefinitionManager(List<ISettingDefinitionProvider> providers) {
        this.settingDefinitions = new HashMap<>();
        providers.forEach(p -> p.define(new SettingDefinitionContext(this.settingDefinitions)));
    }

    @Override
    public SettingDefinition get(String name) throws AbpException{
        SettingDefinition setting = getOrNull(name);
        if (setting == null) {
            throw new AbpException("Undefined setting: " + name);
        }
        return setting;
    }

    @Override
    public List<SettingDefinition> getAll() {
        return new ArrayList<>(settingDefinitions.values());
    }

    @Override
    public SettingDefinition getOrNull(String name) {
        return settingDefinitions.get(name);
    }

}
