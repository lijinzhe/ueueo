package com.ueueo.localization;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class LocalizationSettingHelper {

    private static final String LANGUAGE_SETTING_CULTURE_SPLIT_CHAR = ";";

    /**
     * Gets a setting value like "en-US;en" and returns as splitted values like ("en-US", "en").
     *
     * <param name="settingValue"></param>
     * <returns></returns>
     */
    public static String parseLanguageSettingCultureName(@NonNull String settingValue) {
        Objects.requireNonNull(settingValue);

        if (!settingValue.contains(LANGUAGE_SETTING_CULTURE_SPLIT_CHAR)) {
            return settingValue;
        }

        return settingValue.split(LANGUAGE_SETTING_CULTURE_SPLIT_CHAR)[0];
    }

    public static String parseLanguageSettingUiCultureName(@NonNull String settingValue) {
        Objects.requireNonNull(settingValue);

        if (!settingValue.contains(LANGUAGE_SETTING_CULTURE_SPLIT_CHAR)) {
            return settingValue;
        }

        return settingValue.split(LANGUAGE_SETTING_CULTURE_SPLIT_CHAR)[1];
    }
}
