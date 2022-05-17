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
        List<RequiresFeature> requiresFeatures = getRequiredFeatureAnnotations(context.getMethod());
        for (RequiresFeature requiresFeature : requiresFeatures) {
            this.featureChecker.checkEnabled(requiresFeature.requiresAll(), requiresFeature.features());
        }
    }

    protected boolean isFeatureCheckDisabled(MethodInvocationFeatureCheckerContext context) {
        return context.getMethod().getAnnotation(DisableFeatureCheck.class) != null;
    }

    protected List<RequiresFeature> getRequiredFeatureAnnotations(Method method) {
        List<RequiresFeature> requiresFeatures = new ArrayList<>();
        RequiresFeature requiresFeatureAnnotation = method.getAnnotation(RequiresFeature.class);
        if (requiresFeatureAnnotation != null) {
            requiresFeatures.add(requiresFeatureAnnotation);
        } else {
            RequiresFeatures requiresFeaturesAnnotation = method.getAnnotation(RequiresFeatures.class);
            requiresFeatures.addAll(Arrays.asList(requiresFeaturesAnnotation.requiresFeatures()));
        }
        return requiresFeatures;
    }
}
