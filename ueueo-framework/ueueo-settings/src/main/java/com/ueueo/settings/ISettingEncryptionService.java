package com.ueueo.settings;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 20:33
 */
public interface ISettingEncryptionService {

    String encrypt(SettingDefinition settingDefinition, String plainValue);

    String decrypt(SettingDefinition settingDefinition, String encryptedValue);
}
