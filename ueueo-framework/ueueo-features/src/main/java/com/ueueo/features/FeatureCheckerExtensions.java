package com.ueueo.features;

import com.ueueo.authorization.AuthorizationException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Lee
 * @date 2022-05-29 15:53
 */
public class FeatureCheckerExtensions {

    public static void checkEnabled(IFeatureChecker featureChecker, boolean requiresAll, String[] featureNames) {
        if (featureNames == null || featureNames.length == 0) {
            return;
        }
        if (requiresAll) {
            for (String featureName : featureNames) {
                if (!featureChecker.isEnabled(featureName)) {
                    throw new AuthorizationException(AbpFeatureErrorCodes.AllOfTheseFeaturesMustBeEnabled)
                            .withData("FeatureNames", StringUtils.join(featureNames));
                }
            }
        } else {
            for (String featureName : featureNames) {
                if (featureChecker.isEnabled(featureName)) {
                    return;
                }
            }
            throw new AuthorizationException(AbpFeatureErrorCodes.AtLeastOneOfTheseFeaturesMustBeEnabled)
                    .withData("FeatureNames", StringUtils.join(featureNames));
        }
    }

    public static boolean isEnabled(IFeatureChecker featureChecker, boolean requiresAll, String[] featureNames) {
        if (featureNames == null || featureNames.length == 0) {
            return true;
        }
        if (requiresAll) {
            for (String featureName : featureNames) {
                if (!featureChecker.isEnabled(featureName)) {
                    return false;
                }
            }
            return true;
        } else {
            for (String featureName : featureNames) {
                if (featureChecker.isEnabled(featureName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
