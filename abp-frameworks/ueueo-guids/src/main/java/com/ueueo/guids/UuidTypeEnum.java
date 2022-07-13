package com.ueueo.guids;

/**
 * UUID类型
 *
 * @author Lee
 * @date 2022-06-08 14:19
 */
public enum UuidTypeEnum {

    /**
     * 标准的UUID，长度36
     * 包含数字、大写字母、中划线
     * 格式为：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (8-4-4-4-12)
     * 示例： 63CE3488-31BA-4CDF-8AB3-DDFEDCEC07AA
     */
    STANDARD(36),
    /**
     * 去除标准UUID中的分割线字符，长度32
     * 包含数字、大写字母
     * 示例：
     * E13B54FFC83F476A8882C610FC779EA9
     */
    STANDARD_NO_HYPHEN(32),
    /**
     * 用36位字符集编码，长度26
     * 字符集：0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ
     * 包含数字、大写字母
     * 示例：
     * 201G4XG19C8FM2HX6GKC48ALTH
     * 3SCM0UXAL9ZG82SC70WJIHNLB4
     */
    ENCODE_WITH_36_BIT_ChHAR_SET(26),
    /**
     * 用64位字符集编码，长度22
     * 字符集：0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_
     * 包含数字、大写字母、小写字母、下划线、中划线
     * 示例：
     * PCdzP6W_jm0Aax2Y8l1_9U
     * Hs-S1bEnhQ8Ai5hSWM7fnU
     * H_2-EQJ7grUzC7RDVbnwQs
     */
    ENCODE_WITH_64_BIT_ChHAR_SET(22);

    /**
     * 字符长度
     */
    private int length;

    UuidTypeEnum(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
