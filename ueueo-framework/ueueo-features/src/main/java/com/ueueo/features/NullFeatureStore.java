package com.ueueo.features;

/**
 * @author Lee
 * @date 2022-05-17 16:56
 */
public class NullFeatureStore implements IFeatureStore {
    @Override
    public String getOrNull(String name, String providerName, String providerKey) {
        return null;
    }
}
