package com.ueueo.localization;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Lee
 * @date 2022-06-04 21:54
 */
public class SpringCurrentLocaleAccessor implements ICurrentLocaleAccessor {
    @Override
    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public void setCurrentLocale(Locale locale) {
        LocaleContextHolder.setLocale(locale, true);
    }
}
