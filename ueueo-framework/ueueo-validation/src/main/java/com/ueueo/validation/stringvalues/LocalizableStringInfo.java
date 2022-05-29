package com.ueueo.validation.stringvalues;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-29 16:35
 */
@Data
public class LocalizableStringInfo {
    private String resourceName;

    private String name;

    public LocalizableStringInfo(String resourceName, String name) {
        this.resourceName = resourceName;
        this.name = name;
    }
}
