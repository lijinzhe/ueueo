package com.ueueo.localization;

import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-17 14:54
 */
public class FixedLocalizableString implements ILocalizableString {
    @Getter
    private String value;

    public FixedLocalizableString(String value) {
        this.value = value;
    }

    @Override
    public LocalizedString localize(IStringLocalizerFactory stringLocalizerFactory) {
        return new LocalizedString(value, value);
    }
}
