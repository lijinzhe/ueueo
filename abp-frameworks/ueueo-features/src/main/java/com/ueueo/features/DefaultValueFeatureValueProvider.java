package com.ueueo.features;

/**
 * @author Lee
 * @date 2022-05-17 16:46
 */
public class DefaultValueFeatureValueProvider implements IFeatureValueProvider {
    public static final String ProviderName = "D";

    @Override
    public String getName() {
        return ProviderName;
    }

    @Override
    public String getOrNull(FeatureDefinition feature) {
        return feature.getDefaultValue();
    }
}
