package com.ueueo.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2021-08-18 20:59
 */
public class SettingDefinitionContext implements ISettingDefinitionContext {

    protected Map<String, SettingDefinition> settings;

    public SettingDefinitionContext(Map<String, SettingDefinition> settings) {
        this.settings = settings;
    }

    @Override
    public SettingDefinition getOrNull(String name) {
        return settings.get(name);
    }

    @Override
    public List<SettingDefinition> getAll() {
        return new ArrayList<>(settings.values());
    }

    @Override
    public void add(SettingDefinition... definitions) {
        if (definitions != null) {
            for (SettingDefinition definition : definitions) {
                settings.put(definition.getName(), definition);
            }
        }
    }
}
