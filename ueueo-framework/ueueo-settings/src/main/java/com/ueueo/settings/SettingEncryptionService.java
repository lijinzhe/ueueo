package com.ueueo.settings;

import com.ueueo.security.encryption.IStringEncryptionService;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 21:30
 */
public class SettingEncryptionService implements ISettingEncryptionService {

    protected IStringEncryptionService stringEncryptionService;

    public SettingEncryptionService(IStringEncryptionService stringEncryptionService) {
        this.stringEncryptionService = stringEncryptionService;
    }

    @Override
    public String encrypt(SettingDefinition settingDefinition, String plainValue) {
        if (plainValue != null && plainValue.length() > 0) {
            return stringEncryptionService.encrypt(plainValue, null, null);
        }
        return plainValue;
    }

    @Override
    public String decrypt(SettingDefinition settingDefinition, String encryptedValue) {
        if (encryptedValue != null && encryptedValue.length() > 0) {
            return stringEncryptionService.decrypt(encryptedValue, null, null);
        }
        return encryptedValue;
    }
}
