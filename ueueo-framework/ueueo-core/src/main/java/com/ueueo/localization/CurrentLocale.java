package com.ueueo.localization;

import java.util.Locale;

/**
 * @author Lee
 * @date 2022-06-04 21:48
 */
public class CurrentLocale {

    private final ICurrentLocaleAccessor currentLocaleAccessor;

    public CurrentLocale(ICurrentLocaleAccessor currentLocaleAccessor) {
        this.currentLocaleAccessor = currentLocaleAccessor;
    }

    public Locale get() {
        return currentLocaleAccessor.getCurrentLocale();
    }
}
