package com.ueueo.settings;

import com.ueueo.SystemException;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.*;

/**
 * @author Lee
 * @date 2021-08-18 21:04
 */
@Getter
public class SettingDefinitionManager implements ISettingDefinitionManager {

    protected Map<String, SettingDefinition> settingDefinitions;

    protected AbpSettingOptions options;

    public SettingDefinitionManager(AbpSettingOptions options) {
        this.options = options;
        this.settingDefinitions = new HashMap<>(createSettingDefinitions());
    }

    @Override
    public SettingDefinition get(@NonNull String name) {
        Objects.requireNonNull(name, "name must not null.");
        SettingDefinition setting = getOrNull(name);
        if (setting == null) {
            throw new SystemException("Undefined setting: " + name);
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
        for (ISettingDefinitionProvider provider : options.getDefinitionProviders()) {
            provider.define(new SettingDefinitionContext(settings));
        }
        return settings;
    }
}
