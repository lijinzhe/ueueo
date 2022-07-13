package com.ueueo.localization;

import lombok.Getter;

import java.util.List;

@Getter
public class DefaultLanguageProvider implements ILanguageProvider {

    protected AbpLocalizationOptions options;

    public DefaultLanguageProvider(AbpLocalizationOptions options) {
        this.options = options;
    }

    @Override
    public List<LanguageInfo> getLanguages() {
        return options.getLanguages();
    }

}
