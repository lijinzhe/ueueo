package com.ueueo.features;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-17 16:46
 */
@Getter
public class AbpFeatureOptions {
    private final List<IFeatureDefinitionProvider> definitionProviders;
    private final List<IFeatureValueProvider> valueProviders;

    public AbpFeatureOptions() {
        this.definitionProviders = new ArrayList<>();
        this.valueProviders = new ArrayList<>();
    }
}
