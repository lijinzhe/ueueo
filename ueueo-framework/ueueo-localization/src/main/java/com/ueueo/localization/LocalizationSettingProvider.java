package com.ueueo.localization;

import com.ueueo.localization.resources.AbpLocalization.AbpLocalizationResource;
import com.ueueo.settings.ISettingDefinitionContext;
import com.ueueo.settings.SettingDefinition;
import com.ueueo.settings.SettingDefinitionProvider;

public class LocalizationSettingProvider extends SettingDefinitionProvider {
    @Override
    public void define(ISettingDefinitionContext context) {
        context.add(
                new SettingDefinition(LocalizationSettingNames.DEFAULT_LANGUAGE,
                        "en",
                        L("DisplayName:Abp.Localization.DefaultLanguage"),
                        L("Description:Abp.Localization.DefaultLanguage"),
                        true, false, false)
        );
    }

    private static LocalizableString L(String name) {
        return LocalizableString.create(AbpLocalizationResource.class, name);
    }
}
