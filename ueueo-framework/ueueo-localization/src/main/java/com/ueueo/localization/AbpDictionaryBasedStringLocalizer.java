package com.ueueo.localization;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AbpDictionaryBasedStringLocalizer implements IStringLocalizer, IStringLocalizerSupportsInheritance {
    public LocalizationResource resource;

    public List<IStringLocalizer> baseLocalizers;

    public AbpLocalizationOptions abpLocalizationOptions;

    public AbpDictionaryBasedStringLocalizer(LocalizationResource resource,
                                             List<IStringLocalizer> baseLocalizers,
                                             AbpLocalizationOptions abpLocalizationOptions) {
        this.resource = resource;
        this.baseLocalizers = baseLocalizers;
        this.abpLocalizationOptions = abpLocalizationOptions;
    }

    @Override
    public List<LocalizedString> getAllStrings() {
        return getAllStrings(true);
    }

    @Override
    public List<LocalizedString> getAllStrings(boolean includeBaseLocalizers) {
        //TODO: Can be optimized (example: if it's already default dictionary, skip overriding)
        Map<String, LocalizedString> allStrings = new HashMap<>();
        if (includeBaseLocalizers) {
            for (IStringLocalizer baseLocalizer : baseLocalizers) {
                List<LocalizedString> baseLocalizedString = baseLocalizer.getAllStrings();
                for (LocalizedString localizedString : baseLocalizedString) {
                    allStrings.put(localizedString.getName(), localizedString);
                }
            }
        }
        //Overwrite all strings from the original culture
        resource.getContributors().fill(CultureInfo.currentCulture().getName(), allStrings);
        return new ArrayList<>(allStrings.values());
    }

    @Override
    public LocalizedString get(String name) {
        return getLocalizedString(name);
    }

    @Override
    public LocalizedString get(String name, Object... arguments) {
        return getLocalizedStringFormatted(name, arguments);
    }

    protected LocalizedString getLocalizedStringFormatted(String name, Object... arguments) {
        LocalizedString localizedString = getLocalizedString(name);
        return new LocalizedString(name,
                String.format(localizedString.getValue(), arguments),
                localizedString.isResourceNotFound(),
                localizedString.getSearchedLocation());
    }

    protected LocalizedString getLocalizedString(String name) {
        LocalizedString value = getLocalizedStringOrNull(name, true);
        if (value == null) {
            for (IStringLocalizer baseLocalizer : baseLocalizers) {
                LocalizedString baseLocalizedString = baseLocalizer.get(name);
                if (baseLocalizedString != null && !baseLocalizedString.isResourceNotFound()) {
                    return baseLocalizedString;
                }
            }
            return new LocalizedString(name, name, true);
        }
        return value;
    }

    protected LocalizedString getLocalizedStringOrNull(String name, boolean tryDefaults) {
        String cultureName = CultureInfo.currentCulture().getName();
        //Try to get from original dictionary (with country code)
        LocalizedString strOriginal = resource.getContributors().getOrNull(cultureName, name);
        if (strOriginal != null) {
            return strOriginal;
        }
        if (!tryDefaults) {
            return null;
        }
        if (abpLocalizationOptions.isTryToGetFromBaseCulture()) {
            // TODO Try to get from same language dictionary (without country code)
            //            if (cultureName.contains("-")) //Example: "tr-TR" (length=5)
            //            {
            //
            //                var strLang = Resource.getContributors().getOrNull(CultureHelper.GetBaseCultureName(cultureName), name);
            //                if (strLang != null)
            //                {
            //                    return strLang;
            //                }
            //            }
        }

        if (abpLocalizationOptions.isTryToGetFromDefaultCulture()) {
            //Try to get from default language
            if (StringUtils.isNotBlank(resource.getDefaultCultureName())) {
                LocalizedString strDefault = resource.getContributors().getOrNull(resource.getDefaultCultureName(), name);
                if (strDefault != null) {
                    return strDefault;
                }
            }
        }

        //Not found
        return null;
    }

}
