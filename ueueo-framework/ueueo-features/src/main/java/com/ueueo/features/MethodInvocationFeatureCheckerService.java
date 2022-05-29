package com.ueueo.features;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-17 16:56
 */
public class MethodInvocationFeatureCheckerService implements IMethodInvocationFeatureCheckerService {

    private final IFeatureChecker featureChecker;

    public MethodInvocationFeatureCheckerService(IFeatureChecker featureChecker) {
        this.featureChecker = featureChecker;
    }

    @Override
    public void check(MethodInvocationFeatureCheckerContext context) {
        if (isFeatureCheckDisabled(context)) {
            return;
        }
        List<RequiresFeatureAttribute> requiresFeatures = getRequiredFeatureAnnotations(context.getMethod());
        for (RequiresFeatureAttribute requiresFeature : requiresFeatures) {
            FeatureCheckerExtensions.checkEnabled(featureChecker, requiresFeature.requiresAll(), requiresFeature.features());
        }
    }

    protected boolean isFeatureCheckDisabled(MethodInvocationFeatureCheckerContext context) {
        return context.getMethod().getAnnotation(DisableFeatureCheckAttribute.class) != null;
    }

    protected List<RequiresFeatureAttribute> getRequiredFeatureAnnotations(Method method) {
        List<RequiresFeatureAttribute> requiresFeatures = new ArrayList<>();
        RequiresFeatureAttribute requiresFeatureAnnotation = method.getAnnotation(RequiresFeatureAttribute.class);
        if (requiresFeatureAnnotation != null) {
            requiresFeatures.add(requiresFeatureAnnotation);
        } else {
            RequiresFeaturesAttribute requiresFeaturesAnnotation = method.getAnnotation(RequiresFeaturesAttribute.class);
            requiresFeatures.addAll(Arrays.asList(requiresFeaturesAnnotation.requiresFeatures()));
        }
        return requiresFeatures;
    }
}
