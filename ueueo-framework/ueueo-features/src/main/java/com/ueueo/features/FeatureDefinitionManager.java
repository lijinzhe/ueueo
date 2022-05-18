package com.ueueo.features;

import com.ueueo.AbpException;
import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public class FeatureDefinitionManager implements IFeatureDefinitionManager {

    protected Map<String, FeatureGroupDefinition> featureGroupDefinitions;

    protected Map<String, FeatureDefinition> featureDefinitions;
    @Getter
    protected AbpFeatureOptions options;

    private final BeanFactory beanFactory;

    public FeatureDefinitionManager(AbpFeatureOptions options, BeanFactory beanFactory) {
        this.featureDefinitions = new HashMap<>();
        createFeatureDefinitions();
        this.featureGroupDefinitions = new HashMap<>();
        createFeatureGroupDefinitions();
        this.options = options;
        this.beanFactory = beanFactory;
    }

    @Override
    public FeatureDefinition get(String name) {
        Assert.notNull(name, "name must not null.");
        FeatureDefinition feature = getOrNull(name);
        if (feature == null) {
            throw new AbpException("Undefined feature: " + name);
        }
        return feature;
    }

    @Override
    public List<FeatureDefinition> getAll() {
        return new ArrayList<>(featureDefinitions.values());
    }

    @Override
    public FeatureDefinition getOrNull(String name) {
        return featureDefinitions.get(name);
    }

    @Override
    public List<FeatureGroupDefinition> getGroups() {
        return new ArrayList<>(featureGroupDefinitions.values());
    }

    protected Map<String, FeatureDefinition> createFeatureDefinitions() {
        Map<String, FeatureDefinition> features = new HashMap<>();
        for (FeatureGroupDefinition groupDefinition : featureGroupDefinitions.values()) {
            for (FeatureDefinition feature : groupDefinition.getFeatures()) {
                addFeatureToDictionaryRecursively(features, feature);
            }
        }
        return features;
    }

    protected void addFeatureToDictionaryRecursively(Map<String, FeatureDefinition> features, FeatureDefinition feature) {
        if (features.containsKey(feature.getName())) {
            throw new AbpException("Duplicate feature name: " + feature.getName());
        }
        features.put(feature.getName(), feature);
        for (FeatureDefinition child : feature.getChildren()) {
            addFeatureToDictionaryRecursively(features, child);
        }
    }

    protected Map<String, FeatureGroupDefinition> createFeatureGroupDefinitions() {
        FeatureDefinitionContext context = new FeatureDefinitionContext();
        List<IFeatureDefinitionProvider> providers = options.getDefinitionProviders().stream().map(beanFactory::getBean).collect(Collectors.toList());
        for (IFeatureDefinitionProvider provider : providers) {
            provider.define(context);
        }
        return context.getGroups();
    }
}
