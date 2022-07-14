package com.ueueo.datamodels.management;

import com.ueueo.datamodels.DataModelMeta;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:48
 */
public interface IDataModelManager {
    /**
     * @param name
     * @param providerName
     * @param providerKey
     * @param fallback     default true
     *
     * @return
     */
    String getOrNull(@NonNull String name, @NonNull String providerName, @Nullable String providerKey, boolean fallback);

    /**
     * @param providerName
     * @param providerKey
     * @param fallback     default true
     *
     * @return
     */
    List<DataModelMeta> getAll(@NonNull String providerName, @Nullable String providerKey, boolean fallback);

    /**
     * @param name
     * @param value
     * @param providerName
     * @param providerKey
     * @param forceToSet   default false
     */
    void set(@NonNull String name, @Nullable DataModelMeta value, @NonNull String providerName, @Nullable String providerKey, boolean forceToSet);
}
