package com.ueueo.datamodels;

import com.ueueo.datamodels.IDataModelManager;
import com.ueueo.datamodels.management.IDataModelManagementProvider;
import com.ueueo.datamodels.meta.DataModelMeta;
import com.ueueo.datamodels.definition.IDataModelDefinitionManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-07-15 11:38
 */
public class DataModelManager implements IDataModelManager {

    private final IDataModelDefinitionManager definitionManager;
    private final List<IDataModelManagementProvider> providers;

    public DataModelManager(IDataModelDefinitionManager definitionManager,
                            List<IDataModelManagementProvider> providers) {
        this.definitionManager = definitionManager;
        this.providers = providers;
    }

    @Override
    public String getOrNull(@NonNull String name, @NonNull String providerName, @Nullable String providerKey, boolean fallback) {
        return null;
    }

    @Override
    public List<DataModelMeta> getAll(@NonNull String providerName, @Nullable String providerKey, boolean fallback) {
        return null;
    }

    @Override
    public void set(@NonNull String name, @Nullable DataModelMeta value, @NonNull String providerName, @Nullable String providerKey, boolean forceToSet) {

    }
}


