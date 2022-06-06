package com.ueueo.localization;

import java.util.Locale;

/**
 * @author Lee
 * @date 2022-06-04 21:51
 */
public interface ICurrentLocaleAccessor {
    Locale getCurrentLocale();

    void setCurrentLocale(Locale locale);
}
