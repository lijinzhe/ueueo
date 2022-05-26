package com.ueueo.auditing;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-26 11:17
 */
@Data
public class EntityPropertyChangeInfo {

    /**
     * Maximum length of <see cref="PropertyName"/> property.
     * Value: 96.
     */
    public final int MaxPropertyNameLength = 96;

    /**
     * Maximum length of <see cref="NewValue"/> and <see cref="OriginalValue"/> properties.
     * Value: 512.
     */
    public final int MaxValueLength = 512;

    /**
     * Maximum length of <see cref="PropertyTypeFullName"/> property.
     * Value: 512.
     */
    public final int MaxPropertyTypeFullNameLength = 192;

    private String newValue;

    private String originalValue;

    private String propertyName;

    private String propertyTypeFullName;
}
