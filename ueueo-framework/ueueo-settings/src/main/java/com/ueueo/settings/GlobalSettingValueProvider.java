package com.ueueo.settings;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-19 22:02
 */
public class GlobalSettingValueProvider extends SettingValueProvider {
    public static final String ProviderName = "G";

    public GlobalSettingValueProvider(ISettingStore settingStore) {
        super(settingStore);
    }

    @Override
    public String name() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        return settingStore.getOrNull(setting.getName(), name(), null);
    }

    @Override
    public Collection<SettingValue> getAll(Collection<SettingDefinition> settings) {
        return settingStore.getAll(settings.stream().map(SettingDefinition::getName).collect(Collectors.toList()), name(), null);
    }

}
