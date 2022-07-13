package com.ueueo.features;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2022-05-17 16:53
 */
@Getter
public class FeatureChecker extends FeatureCheckerBase {
    protected AbpFeatureOptions options;
    protected IFeatureDefinitionManager featureDefinitionManager;
    protected List<IFeatureValueProvider> providers;

    public FeatureChecker(AbpFeatureOptions options, IFeatureDefinitionManager featureDefinitionManager) {
        this.featureDefinitionManager = featureDefinitionManager;
        this.options = options;
        this.providers = options.getValueProviders();
    }

    @Override
    public String getOrNull(String name) {
        FeatureDefinition featureDefinition = featureDefinitionManager.get(name);
        List<IFeatureValueProvider> providers = new ArrayList<>(this.providers);
        Collections.reverse(providers);

        if (CollectionUtils.isNotEmpty(featureDefinition.getAllowedProviders())) {
            providers = providers.stream().filter(p -> featureDefinition.getAllowedProviders().contains(p.getName()))
                    .collect(Collectors.toList());
        }

        return getOrNullValueFromProviders(providers, featureDefinition);
    }

    protected String getOrNullValueFromProviders(List<IFeatureValueProvider> providers, FeatureDefinition feature) {
        for (IFeatureValueProvider provider : providers) {
            String value = provider.getOrNull(feature);
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
