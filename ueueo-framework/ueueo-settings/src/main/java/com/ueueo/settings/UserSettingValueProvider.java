package com.ueueo.settings;


import com.ueueo.security.users.ICurrentUser;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-19 21:42
 */
public class UserSettingValueProvider extends SettingValueProvider {

    public static final String ProviderName = "U";

    protected ICurrentUser currentUser;

    public UserSettingValueProvider(ISettingStore settingStore, ICurrentUser currentUser) {
        super(settingStore);
        this.currentUser = currentUser;
    }

    @Override
    public String name() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        return settingStore.getOrNull(setting.getName(), name(), currentUser.getId().toString());
    }

    @Override
    public Collection<SettingValue> getAll(Collection<SettingDefinition> settings) {
        return settingStore.getAll(settings.stream().map(SettingDefinition::getName).collect(Collectors.toList()),
                name(), currentUser.getId().toString());
    }

}
