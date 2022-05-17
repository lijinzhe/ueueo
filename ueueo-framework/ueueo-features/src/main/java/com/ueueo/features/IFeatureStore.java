package com.ueueo.features;

import com.ueueo.features.threading.FeaturesAsyncTaskExecutor;
import org.springframework.lang.NonNull;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-17 16:55
 */
public interface IFeatureStore {
    String getOrNull(@NonNull String name, String providerName, String providerKey);

    default Future<String> getOrNullAsync(@NonNull String name, String providerName, String providerKey) {
        return FeaturesAsyncTaskExecutor.INSTANCE.submit(() -> getOrNull(name, providerName, providerKey));
    }
}
