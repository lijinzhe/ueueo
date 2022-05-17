package com.ueueo.security.encryption;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:00
 */
public class StringEncryptionService implements IStringEncryptionService {
    //TODO by Lee on 2021-08-26 21:01 未实现

    @Getter
    protected final AbpStringEncryptionOptions options;

    public StringEncryptionService(AbpStringEncryptionOptions options) {
        this.options = options;
    }

    @Override
    public String encrypt(String plainText, String passPhrase, byte[] salt) {
        if (plainText == null) {
            return null;
        }
        if (passPhrase == null) {
            passPhrase = options.getDefaultPassPhrase();
        }
        if (salt == null) {
            salt = options.getDefaultSalt();
        }
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        //        using (var password = new Rfc2898DeriveBytes(passPhrase, salt))
        //        {
        //            var keyBytes = password.GetBytes(options.getKeySize() / 8);
        //            using (var symmetricKey = Aes.Create())
        //            {
        //                symmetricKey.Mode = CipherMode.CBC;
        //                using (var encryptor = symmetricKey.CreateEncryptor(keyBytes, Options.InitVectorBytes))
        //                {
        //                    using (var memoryStream = new MemoryStream())
        //                    {
        //                        using (var cryptoStream = new CryptoStream(memoryStream, encryptor, CryptoStreamMode.Write))
        //                        {
        //                            cryptoStream.Write(plainTextBytes, 0, plainTextBytes.Length);
        //                            cryptoStream.FlushFinalBlock();
        //                            var cipherTextBytes = memoryStream.ToArray();
        //                            return Convert.ToBase64String(cipherTextBytes);
        //                        }
        //                    }
        //                }
        //            }
        //        }
        return null;
    }

    @Override
    public String decrypt(String cipherText, String passPhrase, byte[] salt) {
        if (StringUtils.isBlank(cipherText)) {
            return null;
        }
        if (passPhrase == null) {
            passPhrase = options.getDefaultPassPhrase();
        }
        if (salt == null) {
            salt = options.getDefaultSalt();
        }
        //
        //        var cipherTextBytes = Convert.FromBase64String(cipherText);
        //        using (var password = new Rfc2898DeriveBytes(passPhrase, salt))
        //        {
        //            var keyBytes = password.GetBytes(Options.Keysize / 8);
        //            using (var symmetricKey = Aes.Create())
        //            {
        //                symmetricKey.Mode = CipherMode.CBC;
        //                using (var decryptor = symmetricKey.CreateDecryptor(keyBytes, Options.InitVectorBytes))
        //                {
        //                    using (var memoryStream = new MemoryStream(cipherTextBytes))
        //                    {
        //                        using (var cryptoStream = new CryptoStream(memoryStream, decryptor, CryptoStreamMode.Read))
        //                        {
        //                            var plainTextBytes = new byte[cipherTextBytes.Length];
        //                            var totalReadCount = 0;
        //                            while (totalReadCount < cipherTextBytes.Length)
        //                            {
        //                                var buffer = new byte[cipherTextBytes.Length];
        //                                var readCount = cryptoStream.Read(buffer, 0, buffer.Length);
        //                                if (readCount == 0)
        //                                {
        //                                    break;
        //                                }
        //
        //                                for (var i = 0; i < readCount; i++)
        //                                {
        //                                    plainTextBytes[i + totalReadCount] = buffer[i];
        //                                }
        //
        //                                totalReadCount += readCount;
        //                            }
        //
        //                            return Encoding.UTF8.GetString(plainTextBytes, 0, totalReadCount);
        //                        }
        //                    }
        //                }
        //            }
        //        }
        return null;
    }
}
