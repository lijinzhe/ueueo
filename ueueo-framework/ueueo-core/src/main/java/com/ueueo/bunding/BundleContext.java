package com.ueueo.bunding;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-29 11:30
 */
@Data
public class BundleContext {
    private List<BundleDefinition> bundleDefinitions;
    private BundleParameterDictionary parameters;

    public BundleContext() {
        bundleDefinitions = new ArrayList<>();
        parameters = new BundleParameterDictionary();
    }

    /**
     * @param source
     * @param excludeFromBundle    default false
     * @param additionalProperties
     */
    public void add(String source, Boolean excludeFromBundle, Map<String, String> additionalProperties) {
        BundleDefinition bundleDefinition = new BundleDefinition();
        bundleDefinition.setSource(source);
        bundleDefinition.setExcludeFromBundle(excludeFromBundle != null ? excludeFromBundle : false);
        bundleDefinition.setAdditionalProperties(additionalProperties);

        if (bundleDefinitions.stream().noneMatch(item -> item.getSource().equals(bundleDefinition.getSource()))) {
            bundleDefinitions.add(bundleDefinition);
        }
    }
}
