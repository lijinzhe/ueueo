package com.ueueo.security.security.encryption;

import lombok.Data;

import java.nio.charset.StandardCharsets;

/**
 * TODO ABP代码
 *
 * Options used by {@link IStringEncryptionService}
 *
 * @author Lee
 * @date 2021-09-13 11:23
 */
@Data
public class AbpStringEncryptionOptions {

    /**
     * This constant is used to determine the keysize of the encryption algorithm.
     * Default value: 256
     */
    public int keySize;

    /**
     * Default password to encrypt/decrypt texts.
     * It's recommended to set to another value for security.
     * Default value: "gsKnGZ041HLL4IM8"
     */
    public String defaultPassPhrase;

    /**
     * This constant string is used as a "salt" value for the PasswordDeriveBytes function calls.
     * This size of the IV (in bytes) must = (keySize / 8).  Default keySize is 256, so the IV must be
     * 32 bytes long.  Using a 16 character string here gives us 32 bytes when converted to a byte array.
     *
     * Default value: jkE49230Tf093b42
     */
    public byte[] initVectorBytes;

    /**
     * Default value: hgt!16kl
     */
    public byte[] defaultSalt;

    public AbpStringEncryptionOptions() {
        this.keySize = 256;
        this.defaultPassPhrase = "gsKnGZ041HLL4IM8";
        this.initVectorBytes = "jkE49230Tf093b42".getBytes(StandardCharsets.US_ASCII);
        this.defaultSalt = "hgt!16kl".getBytes(StandardCharsets.US_ASCII);
    }
}
