package com.ueueo.features;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.ISimpleStateChecker;
import com.ueueo.simplestatechecking.SimpleStateCheckerContext;

/**
 * @author Lee
 * @date 2022-05-17 16:56
 */
public class RequireFeaturesSimpleStateChecker<TState extends IHasSimpleStateCheckers<TState>> implements ISimpleStateChecker<TState> {

    private IFeatureChecker featureChecker;
    private String[] featureNames;
    private boolean requiresAll;

    public RequireFeaturesSimpleStateChecker(IFeatureChecker featureChecker, String[] featureNames) {
        this.featureChecker = featureChecker;
        this.requiresAll = true;
        this.featureNames = featureNames;
    }

    public RequireFeaturesSimpleStateChecker(IFeatureChecker featureChecker, boolean requiresAll, String[] featureNames) {
        this.featureChecker = featureChecker;
        this.requiresAll = requiresAll;
        this.featureNames = featureNames;
    }

    @Override
    public boolean isEnabled(SimpleStateCheckerContext<TState> context) {
        return featureChecker.isEnabled(requiresAll, featureNames);
    }
}
