package com.ueueo.guids.algorithm;

/**
 * Long转码成字符串工具类
 *
 * @author Lee
 * @date 2022-03-14 17:22
 */
public class LongToStringUtil {
    /** 编码字符集 */
    private static final char[] Alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=".toCharArray();

    private static final String DEFAULT_STRING = "0000000000000";
    private static final Integer DEFAULT_STRING_LENGTH = 13;

    public static String to11String(long number) {
        char[] out = new char[24];
        int tmp = 0, idx = 0;
        // 循环写法
        int bit = 0, bt1 = 8, bt2 = 8;
        int mask = 0x00, offsetm = 0;

        for (; bit < 16; bit += 3, idx += 4) {
            offsetm = 64 - ((bit + 3) << 3);
            tmp = 0;

            if (bt1 > 3) {
                mask = (1 << 8 * 3) - 1;
            } else if (bt1 >= 0) {
                mask = (1 << 8 * bt1) - 1;
                bt2 -= 3 - bt1;
            } else {
                mask = (1 << 8 * (Math.min(bt2, 3))) - 1;
                bt2 -= 3;
            }
            if (bt1 > 0) {
                bt1 -= 3;
                tmp = (int) ((offsetm < 0) ? number : (number >>> offsetm) & mask);
                if (bt1 < 0) {
                    tmp <<= Math.abs(offsetm);
                    mask = (1 << 8 * Math.abs(bt1)) - 1;
                }
            }

            if (bit == 15) {
                out[idx + 3] = Alphabet[64];
                out[idx + 2] = Alphabet[64];
                tmp <<= 4;
            } else {
                out[idx + 3] = Alphabet[tmp & 0x3f];
                tmp >>= 6;
                out[idx + 2] = Alphabet[tmp & 0x3f];
                tmp >>= 6;
            }
            out[idx + 1] = Alphabet[tmp & 0x3f];
            tmp >>= 6;
            out[idx] = Alphabet[tmp & 0x3f];
        }
        return new String(out, 0, 11);
    }

    public static String to13String(long number) {
        String id = Long.toUnsignedString(number, Character.MAX_RADIX).toUpperCase();
        return DEFAULT_STRING.substring(0, DEFAULT_STRING_LENGTH - id.length()) + id;
    }
}
