package com.ueueo.settings;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-19 21:38
 */
public class SettingValueProviderManager implements ISettingValueProviderManager {

    private List<ISettingValueProvider> providers;

    @Override
    public List<ISettingValueProvider> providers() {
        return providers;
    }

    public SettingValueProviderManager(List<ISettingValueProvider> providers) {
        this.providers = providers;
    }
}
