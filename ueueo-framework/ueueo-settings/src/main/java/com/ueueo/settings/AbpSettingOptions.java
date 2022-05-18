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

    private final List<ISettingDefinitionProvider> definitionProviders;
    private final List<ISettingValueProvider> valueProviders;

    public AbpSettingOptions() {
        this.definitionProviders = new ArrayList<>();
        this.valueProviders = new ArrayList<>();
    }
}
