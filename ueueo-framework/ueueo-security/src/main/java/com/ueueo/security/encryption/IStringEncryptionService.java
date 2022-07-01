package com.ueueo.security.encryption;

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
     * @param plainText The text in plain format
     * @param password  A phrase to use as the encryption key (optional, uses default if not provided)
     * @param salt      Salt value (optional, uses default if not provided)
     *
     * @return The text in encrypted format
     */
    String encrypt(String plainText, String password, String salt);

    /**
     * Encrypts a text with default password and salt.
     *
     * @param plainText The text in plain format
     *
     * @return The text in encrypted format
     */
    String encrypt(String plainText);

    /**
     * Decrypts a text that is encrypted by the <see cref="Encrypt"/> method.
     *
     * @param encryptedText The text in encrypted format
     * @param password      A phrase to use as the encryption key (optional, uses default if not provided)
     * @param salt          Salt value (optional, uses default if not provided)
     *
     * @return The text in plain format
     */
    String decrypt(String encryptedText, String password, String salt);

    /**
     * Decrypts a text with default password and salt.
     *
     * @param encryptedText The text in encrypted format
     *
     * @return The text in plain format
     */
    String decrypt(String encryptedText);
}
