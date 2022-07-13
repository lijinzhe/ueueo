package com.ueueo.localization;

import java.util.Map;

/**
 * Represents a dictionary that is used to find a localized string.
 */
public interface ILocalizationDictionary {
    String getCultureName();

    LocalizedString getOrNull(String name);

    void fill(Map<String, LocalizedString> dictionary);
}
