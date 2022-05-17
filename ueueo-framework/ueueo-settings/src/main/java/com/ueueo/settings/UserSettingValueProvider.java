package com.ueueo.settings;

import com.ueueo.users.ICurrentUser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
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
    public String getName() {
        return ProviderName;
    }

    @Override
    public String getOrNull(SettingDefinition setting) {
        if (currentUser != null && currentUser.getId() != null) {
            return settingStore.getOrNull(setting.getName(), getName(), currentUser.getId().toString());
        } else {
            return null;
        }
    }

    @Override
    public List<SettingValue> getAll(List<SettingDefinition> settings) {
        if (currentUser != null && currentUser.getId() != null) {
            return settingStore.getAll(settings.stream().map(SettingDefinition::getName).collect(Collectors.toList()),
                    getName(), currentUser.getId().toString());
        } else {
            return Collections.emptyList();
        }
    }

}
