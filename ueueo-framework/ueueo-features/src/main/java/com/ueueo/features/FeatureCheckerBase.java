package com.ueueo.features;

import com.ueueo.AbpException;

/**
 * @author Lee
 * @date 2022-05-17 16:53
 */
public abstract class FeatureCheckerBase implements IFeatureChecker {

    @Override
    public boolean isEnabled(String name) {
        String value = getOrNull(name);
        if (value == null) {
            return false;
        }
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            throw new AbpException(String.format("The value '%s' for the feature '%s' should be a boolean, but was not!", value, name), e);
        }
    }
}
