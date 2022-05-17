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
    private List<Class<? extends IFeatureDefinitionProvider>> definitionProviders;
    private List<Class<? extends IFeatureValueProvider>> valueProviders;

    public AbpFeatureOptions() {
        this.definitionProviders = new ArrayList<>();
        this.valueProviders = new ArrayList<>();
    }
}
