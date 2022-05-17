package com.ueueo.features;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public interface IFeatureDefinitionManager {
    @NonNull
    FeatureDefinition get(@NonNull String name);

    List<FeatureDefinition> getAll();

    FeatureDefinition getOrNull(String name);

    List<FeatureGroupDefinition> getGroups();
}
