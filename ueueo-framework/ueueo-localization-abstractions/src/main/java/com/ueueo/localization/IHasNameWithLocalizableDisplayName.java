package com.ueueo.localization;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-17 14:55
 */
public interface IHasNameWithLocalizableDisplayName {
    @NonNull
    String getName();

    @Nullable
    ILocalizableString getDisplayName();

    default String getLocalizedDisplayName(@NonNull IStringLocalizerFactory stringLocalizerFactory, @Nullable String localizationNamePrefix) {
        if (getDisplayName() != null) {
            return getDisplayName().localize(stringLocalizerFactory).getValue();
        }
        IStringLocalizer defaultStringLocalizer = null;
        if (stringLocalizerFactory instanceof IAbpStringLocalizerFactoryWithDefaultResourceSupport) {
            defaultStringLocalizer = ((IAbpStringLocalizerFactoryWithDefaultResourceSupport) stringLocalizerFactory).createDefaultOrNull();
        }
        if (defaultStringLocalizer == null) {
            return getName();
        }

        LocalizedString localizedString = defaultStringLocalizer.get(String.format("%s%s", localizationNamePrefix, getName()));
        if (!localizedString.isResourceNotFound() || StringUtils.isEmpty(localizationNamePrefix)) {
            return localizedString.getValue();
        }

        return defaultStringLocalizer.get(getName()).getValue();
    }
}
