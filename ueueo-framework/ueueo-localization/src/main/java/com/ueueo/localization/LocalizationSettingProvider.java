package com.ueueo.localization;

import com.ueueo.settings.ISettingDefinitionContext;
import com.ueueo.settings.SettingDefinition;
import com.ueueo.settings.SettingDefinitionProvider;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 14:44
 */
public class LocalizationSettingProvider extends SettingDefinitionProvider {
    @Override
    public void define(ISettingDefinitionContext context) {
        context.add(new SettingDefinition("Abp.Localization.DefaultLanguage",
                "cn", "简体中文", "简体中文",
                true, true, false));
    }
}
