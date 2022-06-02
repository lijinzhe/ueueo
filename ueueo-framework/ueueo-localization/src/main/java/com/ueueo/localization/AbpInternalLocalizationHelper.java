package com.ueueo.localization;

import java.util.List;

/**
 * This class is designed to be used internal by the framework.
 */
public class AbpInternalLocalizationHelper {
    /**
     * Searches an array of keys in an array of localizers.
     *
     * <param name="localizers">
     * An array of localizers. Search the keys on the localizers.
     * Can contain null items in the array.
     * </param>
     * <param name="keys">
     * An array of keys. Search the keys on the localizers.
     * Should not contain null items in the array.
     * </param>
     * <param name="defaultValue">
     * Return value if none of the localizers has none of the keys.
     * </param>
     * <returns></returns>
     */
    public static String localizeWithFallback(
            List<IStringLocalizer> localizers,
            List<String> keys,
            String defaultValue) {
        for (String key : keys) {
            for (IStringLocalizer localizer : localizers) {
                if (localizer == null) {
                    continue;
                }

                LocalizedString localizedString = localizer.get(key);
                if (!localizedString.isResourceNotFound()) {
                    return localizedString.getValue();
                }
            }
        }

        return defaultValue;
    }
}
