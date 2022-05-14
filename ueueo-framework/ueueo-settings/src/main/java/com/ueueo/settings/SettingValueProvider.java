package com.ueueo.settings;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-19 21:36
 */
public abstract class SettingValueProvider implements ISettingValueProvider {

    protected ISettingStore settingStore;

    public ISettingStore getSettingStore() {
        return settingStore;
    }

    protected SettingValueProvider(ISettingStore settingStore) {
        this.settingStore = settingStore;
    }

}
