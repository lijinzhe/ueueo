package com.ueueo.settings;

import com.ueueo.settings.threading.SettingsAsyncTaskExecutor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2021-08-18 20:36
 */
public interface ISettingValueProvider {
    String getName();

    String getOrNull(@NonNull SettingDefinition setting);

    List<SettingValue> getAll(@NonNull List<SettingDefinition> settings);

    default Future<String> getOrNullAsync(@NonNull SettingDefinition setting) {
        return SettingsAsyncTaskExecutor.INSTANCE.submit(() -> getOrNull(setting));
    }

    default Future<List<SettingValue>> getAllAsync(@NonNull List<SettingDefinition> settings) {
        return SettingsAsyncTaskExecutor.INSTANCE.submit(() -> getAll(settings));
    }
}
