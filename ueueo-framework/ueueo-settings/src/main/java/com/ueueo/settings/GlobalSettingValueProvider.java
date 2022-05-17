package com.ueueo.settings;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-19 22:02
 */
public class GlobalSettingValueProvider extends SettingValueProvider {
    public static final String ProviderName = "G";

    public GlobalSettingValueProvider(ISettingStore settingStore) {
        super(settingStore);
    }

    @Override
    public String getName() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        return settingStore.getOrNull(setting.getName(), getName(), null);
    }

    @Override
    public List<SettingValue> getAll(List<SettingDefinition> settings) {
        return settingStore.getAll(settings.stream().map(SettingDefinition::getName).collect(Collectors.toList()), getName(), null);
    }
}
