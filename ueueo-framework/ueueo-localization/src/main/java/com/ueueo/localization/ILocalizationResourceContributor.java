package com.ueueo.localization;

import java.util.Map;

public interface ILocalizationResourceContributor
{
    void initialize(LocalizationResourceInitializationContext context);

    LocalizedString getOrNull(String cultureName, String name);

    void fill(String cultureName, Map<String, LocalizedString> dictionary);
}
