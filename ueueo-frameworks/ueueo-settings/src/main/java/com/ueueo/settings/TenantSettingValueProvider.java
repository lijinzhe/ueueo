package com.ueueo.settings;

import com.ueueo.multitenancy.ICurrentTenant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-19 21:42
 */
public class TenantSettingValueProvider extends SettingValueProvider {

    public static final String ProviderName = "T";

    protected ICurrentTenant currentTenant;

    public TenantSettingValueProvider(ISettingStore settingStore, ICurrentTenant currentTenant) {
        super(settingStore);
        this.currentTenant = currentTenant;
    }

    @Override
    public String getName() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        return settingStore.getOrNull(setting.getName(), getName(), currentTenant.getId().toString());
    }

    @Override
    public List<SettingValue> getAll(List<SettingDefinition> settings) {
        return settingStore.getAll(settings.stream().map(SettingDefinition::getName).collect(Collectors.toList()),
                getName(), currentTenant.getId().toString());
    }

}
