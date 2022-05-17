package com.ueueo.features;

import com.ueueo.features.threading.FeaturesAsyncTaskExecutor;
import org.springframework.lang.NonNull;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public interface IFeatureValueProvider {
    String getName();

    String getOrNull(@NonNull FeatureDefinition feature);

    default Future<String> getOrNullAsync(@NonNull FeatureDefinition feature) {
        return FeaturesAsyncTaskExecutor.INSTANCE.submit(() -> getOrNull(feature));
    }
}
