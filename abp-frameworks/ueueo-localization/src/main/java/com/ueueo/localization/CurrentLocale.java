package com.ueueo.localization;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Lee
 * @date 2022-06-04 21:48
 */
public class CurrentLocale {

    public static Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    public static void setCurrentLocale(Locale locale) {
        LocaleContextHolder.setLocale(locale, true);
    }
}
