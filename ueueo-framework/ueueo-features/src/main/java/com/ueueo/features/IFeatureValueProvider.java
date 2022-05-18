package com.ueueo.features;

import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public interface IFeatureValueProvider {
    String getName();

    String getOrNull(@NonNull FeatureDefinition feature);

}
