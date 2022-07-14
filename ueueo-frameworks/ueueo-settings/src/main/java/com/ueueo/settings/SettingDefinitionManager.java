package com.ueueo.settings;

import com.ueueo.exception.BaseException;
import com.ueueo.util.Check;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2021-08-18 21:04
 */
@Getter
public class SettingDefinitionManager implements ISettingDefinitionManager {

    protected final Map<String, SettingDefinition> settingDefinitions;

    private final List<ISettingDefinitionProvider> definitionProviders;

    public SettingDefinitionManager(List<ISettingDefinitionProvider> definitionProviders) {
        this.definitionProviders = definitionProviders;
        this.settingDefinitions = new HashMap<>(createSettingDefinitions());
    }

    @NonNull
    @Override
    public SettingDefinition get(@NonNull String name) {
        Check.notNullOrEmpty(name, "name");
        SettingDefinition setting = getOrNull(name);
        if (setting == null) {
            throw new BaseException("Undefined setting: " + name);
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

    protected Map<String, SettingDefinition> createSettingDefinitions() {
        Map<String, SettingDefinition> settings = new HashMap<>();
        for (ISettingDefinitionProvider provider : definitionProviders) {
            provider.define(new SettingDefinitionContext(settings));
        }
        return settings;
    }
}
