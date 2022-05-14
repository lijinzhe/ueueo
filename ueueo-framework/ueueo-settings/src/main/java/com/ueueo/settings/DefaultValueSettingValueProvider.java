package com.ueueo.settings;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-19 22:02
 */
public class DefaultValueSettingValueProvider extends SettingValueProvider {
    public static final String ProviderName = "D";

    public DefaultValueSettingValueProvider(ISettingStore settingStore) {
        super(settingStore);
    }

    @Override
    public String name() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        return setting.getDefaultValue();
    }

    @Override
    public Collection<SettingValue> getAll(Collection<SettingDefinition> settings) {
        return settings.stream().map(def -> new SettingValue(def.getName(), def.getDefaultValue()))
                .collect(Collectors.toList());
    }

}
