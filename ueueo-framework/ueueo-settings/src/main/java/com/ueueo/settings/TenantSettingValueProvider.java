package com.ueueo.settings;


import com.ueueo.multitenancy.ICurrentTenant;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
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
    public String name() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        return settingStore.getOrNull(setting.getName(), name(), currentTenant.getId().toString());
    }

    @Override
    public Collection<SettingValue> getAll(Collection<SettingDefinition> settings) {
        return settingStore.getAll(settings.stream().map(SettingDefinition::getName).collect(Collectors.toList()),
                name(), currentTenant.getId().toString());
    }

}
