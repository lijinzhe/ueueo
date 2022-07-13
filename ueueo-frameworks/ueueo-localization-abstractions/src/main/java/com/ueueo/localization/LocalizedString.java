package com.ueueo.localization;

import lombok.Data;

/**
 * C#  Microsoft.Extensions.Localization;
 * A locale specific string.
 *
 * @author Lee
 * @date 2021-09-13 21:31
 */
@Data
public class LocalizedString implements CharSequence {
    /**
     * The name of the string in the resource it was loaded from.
     */
    private String name;
    /**
     * The actual string.
     */
    private String value;
    /**
     * Whether the string was not found in a resource.
     * If true, an alternate string value was used.
     */
    private boolean resourceNotFound;

    public LocalizedString(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public LocalizedString(String name, String value, boolean resourceNotFound) {
        this.name = name;
        this.value = value;
        this.resourceNotFound = resourceNotFound;
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int index) {
        return value.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return value.subSequence(start, end);
    }
}
