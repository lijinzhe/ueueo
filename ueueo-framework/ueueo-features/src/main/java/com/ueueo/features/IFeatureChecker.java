package com.ueueo.features;

import com.ueueo.authorization.AbpAuthorizationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public interface IFeatureChecker {

    String getOrNull(@NonNull String name);

    boolean isEnabled(String name);

    default void checkEnabled(boolean requiresAll, String[] featureNames) {
        if (featureNames == null || featureNames.length == 0) {
            return;
        }
        if (requiresAll) {
            for (String featureName : featureNames) {
                if (!isEnabled(featureName)) {
                    throw new AbpAuthorizationException(AbpFeatureErrorCodes.AllOfTheseFeaturesMustBeEnabled)
                            .withData("FeatureNames", StringUtils.join(featureNames));
                }
            }
        } else {
            for (String featureName : featureNames) {
                if (isEnabled(featureName)) {
                    return;
                }
            }
            throw new AbpAuthorizationException(AbpFeatureErrorCodes.AtLeastOneOfTheseFeaturesMustBeEnabled)
                    .withData("FeatureNames", StringUtils.join(featureNames));
        }
    }

    default boolean isEnabled(boolean requiresAll, String[] featureNames) {
        if (featureNames == null || featureNames.length == 0) {
            return true;
        }
        if (requiresAll) {
            for (String featureName : featureNames) {
                if (!isEnabled(featureName)) {
                    return false;
                }
            }
            return true;
        } else {
            for (String featureName : featureNames) {
                if (isEnabled(featureName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
