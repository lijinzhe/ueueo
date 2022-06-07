package com.ueueo.multilingualobjects;

import com.ueueo.localization.LocalizationSettingNames;
import com.ueueo.settings.ISettingProvider;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

@Getter
public class MultiLingualObjectManager implements IMultiLingualObjectManager {
    protected final ISettingProvider SettingProvider;

    protected final int MaxCultureFallbackDepth = 5;

    public MultiLingualObjectManager(ISettingProvider settingProvider) {
        SettingProvider = settingProvider;
    }

    @Override
    public <TTranslation extends IObjectTranslation> TTranslation getTranslation(
            IMultiLingualObject<TTranslation> multiLingual,
            String culture
    ) {
        //TODO 获取当前山下文中的语言文化
        // culture ??= CultureInfo.CurrentUICulture.Name;

        if (CollectionUtils.isEmpty(multiLingual.getTranslations())) {
            return null;
        }

        TTranslation translation = multiLingual.getTranslations()
                .stream().filter(pt -> pt.getLanguage().equals(culture))
                .findFirst().orElse(null);
        if (translation != null) {
            return translation;
        }

        String defaultLanguage = SettingProvider.getOrNull(LocalizationSettingNames.DEFAULT_LANGUAGE);

        translation = multiLingual.getTranslations()
                .stream().filter(pt -> pt.getLanguage().equals(defaultLanguage))
                .findFirst().orElse(null);
        if (translation != null) {
            return translation;
        }

        translation = multiLingual.getTranslations().get(0);
        return translation;
    }

}
