package com.ueueo.security.security.encryption;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:00
 */
public class StringEncryptionService implements IStringEncryptionService {
    //TODO by Lee on 2021-08-26 21:01 未实现

    private AbpStringEncryptionOptions options;

    @Override
    public String encrypt(String plainText, String passPhrase, byte[] salt) {
        return null;
    }

    @Override
    public String decrypt(String cipherText, String passPhrase, byte[] salt) {
        return null;
    }
}
