package com.ueueo.features;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.ISimpleStateChecker;
import com.ueueo.simplestatechecking.SimpleStateCheckerContext;

/**
 * @author Lee
 * @date 2022-05-17 16:56
 */
public class RequireFeaturesSimpleStateChecker<TState extends IHasSimpleStateCheckers<TState>> implements ISimpleStateChecker<TState> {

    private String[] featureNames;
    private boolean requiresAll;

    public RequireFeaturesSimpleStateChecker(String[] featureNames) {
        this.requiresAll = true;
        this.featureNames = featureNames;
    }

    public RequireFeaturesSimpleStateChecker(boolean requiresAll, String[] featureNames) {
        this.requiresAll = requiresAll;
        this.featureNames = featureNames;
    }

    @Override
    public boolean isEnabled(SimpleStateCheckerContext<TState> context) {
        IFeatureChecker featureChecker = context.getBeanFactory().getBean(IFeatureChecker.class);
        return featureChecker.isEnabled(requiresAll, featureNames);
    }
}
