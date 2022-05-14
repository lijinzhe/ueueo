package com.ueueo.security.security.encryption;

/**
 * TODO ABP代码
 * Can be used to simply encrypt/decrypt texts.
 * Use <see cref="AbpStringEncryptionOptions"/> to configure default values.
 *
 * @author Lee
 * @date 2021-08-18 21:31
 */
public interface IStringEncryptionService {

    /**
     * Encrypts a text.
     *
     * @param plainText  The text in plain format
     * @param passPhrase A phrase to use as the encryption key (optional, uses default if not provided)
     * @param salt       Salt value (optional, uses default if not provided)
     *
     * @return
     */
    String encrypt(String plainText, String passPhrase, byte[] salt);

    /**
     * Decrypts a text that is encrypted by the <see cref="Encrypt"/> method.
     *
     * @param cipherText The text in encrypted format
     * @param passPhrase A phrase to use as the encryption key (optional, uses default if not provided)
     * @param salt       Salt value (optional, uses default if not provided)
     *
     * @return
     */
    String decrypt(String cipherText, String passPhrase, byte[] salt);
}
