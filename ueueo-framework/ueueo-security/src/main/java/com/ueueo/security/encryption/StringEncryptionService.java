package com.ueueo.security.encryption;

import com.ueueo.util.Check;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * @author Lee
 * @date 2021-08-26 21:00
 */
public class StringEncryptionService implements IStringEncryptionService {

    /**
     * Default password to encrypt/decrypt texts.
     * It's recommended to set to another value for security.
     * Default value: "MaI1sje0ZG4L1l28"
     */
    private String defaultPassword = "MaI1sje0ZG4L1l28";

    /**
     * Default value: i9o_n2q2
     */
    private String defaultSalt = "i9o_n2q2a!2";

    public StringEncryptionService() {
    }

    public StringEncryptionService(String password, String salt) {
        Check.notNullOrEmpty(password, "password", 20, 10);
        Check.notNullOrEmpty(salt, "salt", 20, 10);
        this.defaultPassword = password;
        this.defaultSalt = salt;
    }

    @Override
    public String encrypt(String plainText, String password, String salt) {
        if (plainText == null) {
            return null;
        }
        if (StringUtils.isBlank(password)) {
            password = defaultPassword;
        }
        if (StringUtils.isBlank(salt)) {
            salt = defaultSalt;
        }
        TextEncryptor encryptor = Encryptors.text(password, salt);
        return encryptor.encrypt(plainText);
    }

    @Override
    public String decrypt(String encryptedText, String password, String salt) {
        if (StringUtils.isBlank(encryptedText)) {
            return null;
        }
        if (StringUtils.isBlank(password)) {
            password = defaultPassword;
        }
        if (StringUtils.isBlank(salt)) {
            salt = defaultSalt;
        }
        TextEncryptor encryptor = Encryptors.text(password, salt);
        return encryptor.decrypt(encryptedText);
    }

    @Override
    public String encrypt(String plainText) {
        return encrypt(plainText, defaultPassword, defaultSalt);
    }

    @Override
    public String decrypt(String encryptedText) {
        return decrypt(encryptedText, defaultPassword, defaultSalt);
    }
}
