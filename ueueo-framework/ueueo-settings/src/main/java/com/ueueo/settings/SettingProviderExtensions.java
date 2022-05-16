package com.ueueo.settings;

import com.ueueo.AbpException;

import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-19 19:39
 */
public class SettingProviderExtensions {
    public static Boolean isTrue(ISettingProvider settingProvider, String name)  throws AbpException {
        Objects.requireNonNull(settingProvider);
        Objects.requireNonNull(name);
        return name.equalsIgnoreCase(settingProvider.getOrNull(name));
    }

    public static String get(ISettingProvider settingProvider, String name, String defaultValue) throws AbpException {
        Objects.requireNonNull(settingProvider);
        Objects.requireNonNull(name);
        String value = settingProvider.getOrNull(name);
        return value != null ? value : defaultValue;
    }
}
