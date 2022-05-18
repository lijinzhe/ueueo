package com.ueueo.settings;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-19 21:38
 */
public class SettingValueProviderManager implements ISettingValueProviderManager {

    private final List<ISettingValueProvider> providers;
    protected AbpSettingOptions options;

    @Override
    public List<ISettingValueProvider> getProviders() {
        return providers;
    }

    public SettingValueProviderManager(AbpSettingOptions options) {
        this.options = options;
        this.providers = options.getValueProviders();
    }
}
