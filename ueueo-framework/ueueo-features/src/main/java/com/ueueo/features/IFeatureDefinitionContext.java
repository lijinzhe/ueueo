package com.ueueo.features;

import com.ueueo.localization.ILocalizableString;
import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public interface IFeatureDefinitionContext {

    FeatureGroupDefinition addGroup(@NonNull String name, ILocalizableString displayName);

    FeatureGroupDefinition getGroupOrNull(@NonNull String name);

    void removeGroup(@NonNull String name);
}
