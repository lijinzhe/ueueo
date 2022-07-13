package com.ueueo.features;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;

import java.util.Objects;

/**
 * @author Lee
 * @date 2022-05-29 16:04
 */
public class FeatureSimpleStateCheckerExtensions {

    public static <TState extends IHasSimpleStateCheckers<TState>> TState requireFeatures(TState state,
                                                                                          IFeatureChecker featureChecker,

                                                                                          String[] features) {
        requireFeatures(state, featureChecker, true, features);
        return state;
    }

    public static <TState extends IHasSimpleStateCheckers<TState>> TState requireFeatures(TState state,
                                                                                          IFeatureChecker featureChecker,
                                                                                          boolean requiresAll,
                                                                                          String[] features) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(features);
        state.getStateCheckers().add(new RequireFeaturesSimpleStateChecker(featureChecker, requiresAll, features));
        return state;
    }
}
