package com.ueueo.localization;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Data
public class LanguageInfo implements ILanguageInfo {

    public String name;

    public String displayName;

    public String flagIcon;

    protected LanguageInfo() {

    }

    public LanguageInfo(String name, String displayName, String flagIcon) {
        changeCultureInternal(name, displayName);
        this.flagIcon = flagIcon;
    }

    public void changeCulture(String name, String displayName) {
        changeCultureInternal(name, displayName);
    }

    private void changeCultureInternal(String name, String displayName) {
        Objects.requireNonNull(name);
        this.name = name;
        this.displayName = StringUtils.isNoneBlank(displayName)
                ? displayName
                : name;
    }
}
