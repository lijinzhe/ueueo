package com.ueueo.settings;

import com.ueueo.settings.threading.SettingsAsyncTaskExecutor;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2021-08-18 20:35
 */
public interface ISettingStore {

    String getOrNull(String name, String providerName, String providerKey);

    List<SettingValue> getAll(List<String> names, String providerName, String providerKey);

    default Future<String> getOrNullAsync(String name, String providerName, String providerKey) {
        return SettingsAsyncTaskExecutor.INSTANCE.submit(() -> getOrNull(name, providerName, providerKey));
    }

    default Future<List<SettingValue>> getAllAsync(List<String> names, String providerName, String providerKey) {
        return SettingsAsyncTaskExecutor.INSTANCE.submit(() -> getAll(names, providerName, providerKey));
    }
}
