package com.ueueo.localization;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public class LanguageInfoExtensions {

    public static <T extends ILanguageInfo> T findByCulture(
            @NonNull List<T> languages,
            @NonNull String cultureName,
            @Nullable String uiCultureName) {
        String uiCulture = StringUtils.isNoneBlank(uiCultureName) ? uiCultureName : cultureName;

        T language = languages.stream().filter(l -> l.getCultureName().equals(cultureName)
                        && l.getUiCultureName().equals(uiCulture))
                .findFirst()
                .orElse(null);
        if (language == null) {
            language = languages.stream().filter(l -> l.getCultureName().equals(cultureName))
                    .findFirst()
                    .orElse(null);
        }
        if (language == null) {
            language = languages.stream().filter(l -> l.getUiCultureName().equals(uiCulture))
                    .findFirst()
                    .orElse(null);
        }
        return language;
    }
}
