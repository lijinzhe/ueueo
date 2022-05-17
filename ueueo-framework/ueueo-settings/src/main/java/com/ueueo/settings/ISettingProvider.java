package com.ueueo.settings;

import com.ueueo.settings.threading.SettingsAsyncTaskExecutor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2021-08-18 20:33
 */
public interface ISettingProvider {

    String getOrNull(String name);

    List<SettingValue> getAll(@NonNull List<String> names);

    List<SettingValue> getAll();

    default Future<String> getOrNullAsync(String name) {
        return SettingsAsyncTaskExecutor.INSTANCE.submit(() -> getOrNull(name));
    }

    default Future<List<SettingValue>> getAllAsync(@NonNull List<String> names) {
        return SettingsAsyncTaskExecutor.INSTANCE.submit(() -> getAll(names));
    }

    default Future<List<SettingValue>> getAllAsync() {
        return SettingsAsyncTaskExecutor.INSTANCE.submit(() -> getAll());
    }

}
