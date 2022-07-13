package com.ueueo.settings;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-19 22:02
 */
public class DefaultValueSettingValueProvider extends SettingValueProvider {
    public static final String ProviderName = "D";

    public DefaultValueSettingValueProvider(ISettingStore settingStore) {
        super(settingStore);
    }

    @Override
    public String getName() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        return setting.getDefaultValue();
    }

    @Override
    public List<SettingValue> getAll(List<SettingDefinition> settings) {
        return settings.stream().map(sd -> new SettingValue(sd.getName(), sd.getDefaultValue())).collect(Collectors.toList());
    }
}
