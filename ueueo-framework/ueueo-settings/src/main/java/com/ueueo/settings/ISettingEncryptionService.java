package com.ueueo.settings;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2021-08-18 20:33
 */
public interface ISettingEncryptionService {
    @Nullable
    String encrypt(@NonNull SettingDefinition settingDefinition, @Nullable String plainValue);

    @Nullable
    String decrypt(@NonNull SettingDefinition settingDefinition, @Nullable String encryptedValue);
}
