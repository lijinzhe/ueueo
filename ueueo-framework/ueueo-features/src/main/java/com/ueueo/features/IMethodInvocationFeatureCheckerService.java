package com.ueueo.features;

import com.ueueo.features.threading.FeaturesAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public interface IMethodInvocationFeatureCheckerService {
    void check(MethodInvocationFeatureCheckerContext context);

    default Future<?> checkAsync(MethodInvocationFeatureCheckerContext context) {
        return FeaturesAsyncTaskExecutor.INSTANCE.submit(() -> check(context));
    }
}
