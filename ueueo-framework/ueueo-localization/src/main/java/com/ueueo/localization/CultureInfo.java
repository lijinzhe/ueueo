package com.ueueo.localization;

import lombok.Data;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-06-02 14:58
 */
@Data
public class CultureInfo {

    private String name;

    public CultureInfo(String name) {
        this.name = name;
    }

    public static CultureInfo currentCulture() {
        return new CultureInfo("zh");
    }

    public static CultureInfo currentUiCulture() {
        return new CultureInfo("zh");
    }
}
