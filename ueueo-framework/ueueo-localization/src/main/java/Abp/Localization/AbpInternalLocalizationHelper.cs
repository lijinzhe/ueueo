﻿using Microsoft.Extensions.Localization;

namespace Volo.Abp.Localization;

/**
 * This class is designed to be used internal by the framework.
*/
public static class AbpInternalLocalizationHelper
{
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
    public static String LocalizeWithFallback(
        IStringLocalizer[] localizers,
        String[] keys,
        String defaultValue)
    {
        for (var key in keys)
        {
            for (var localizer in localizers)
            {
                if (localizer == null)
                {
                    continue;
                }

                var localizedString = localizer[key];
                if (!localizedString.ResourceNotFound)
                {
                    return localizedString.Value;
                }
            }
        }

        return defaultValue;
    }
}
