package com.ueueo.localization;

import java.util.ArrayList;
import java.util.Map;

public class LocalizationResourceContributorList extends ArrayList<ILocalizationResourceContributor> {

    public LocalizedString getOrNull(String cultureName, String name) {
        for (ILocalizationResourceContributor contributor : this) {
            LocalizedString localString = contributor.getOrNull(cultureName, name);
            if (localString != null) {
                return localString;
            }
        }

        return null;
    }

    public void fill(String cultureName, Map<String, LocalizedString> dictionary) {
        for (ILocalizationResourceContributor contributor : this) {
            contributor.fill(cultureName, dictionary);
        }
    }
}
