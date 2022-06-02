package com.ueueo.localization;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Data
public class LanguageInfo implements ILanguageInfo {

    public String cultureName;

    public String uiCultureName;

    public String displayName;

    public String flagIcon;

    protected LanguageInfo() {

    }

    public LanguageInfo(String cultureName, String uiCultureName, String displayName, String flagIcon) {
        changeCultureInternal(cultureName, uiCultureName, displayName);
        this.flagIcon = flagIcon;
    }

    public void changeCulture(String cultureName, String uiCultureName, String displayName) {
        changeCultureInternal(cultureName, uiCultureName, displayName);
    }

    private void changeCultureInternal(String cultureName, String uiCultureName, String displayName) {
        Objects.requireNonNull(cultureName);
        this.cultureName = cultureName;
        this.uiCultureName = StringUtils.isNoneBlank(uiCultureName)
                ? uiCultureName
                : cultureName;
        this.displayName = StringUtils.isNoneBlank(displayName)
                ? displayName
                : cultureName;
    }
}
