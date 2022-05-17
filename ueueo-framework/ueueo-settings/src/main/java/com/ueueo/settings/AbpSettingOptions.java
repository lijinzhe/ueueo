package com.ueueo.settings;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-17 11:38
 */
@Getter
public class AbpSettingOptions {
    private List<Class<? extends ISettingDefinitionProvider>> definitionProviders;
    private List<Class<? extends ISettingValueProvider>> valueProviders;

    public AbpSettingOptions() {
        this.definitionProviders = new ArrayList<>();
        this.valueProviders = new ArrayList<>();
    }
}
