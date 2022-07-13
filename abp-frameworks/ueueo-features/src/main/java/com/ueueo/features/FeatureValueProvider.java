package com.ueueo.features;

import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public abstract class FeatureValueProvider implements IFeatureValueProvider {
    @Getter
    protected final IFeatureStore featureStore;

    public FeatureValueProvider(IFeatureStore featureStore) {
        this.featureStore = featureStore;
    }
}
