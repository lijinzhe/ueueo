package com.ueueo.datamodels.management;

import com.ueueo.datamodels.DataModelDefinition;
import com.ueueo.datamodels.DataModelMeta;
import org.springframework.lang.NonNull;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-07-14 17:55
 */
public abstract class DataModelManagementProvider implements IDataModelManagementProvider {

    protected IDataModelManagementStore dataModelManagementStore;

    protected DataModelManagementProvider(IDataModelManagementStore dataModelManagementStore) {
        this.dataModelManagementStore = dataModelManagementStore;
    }

    @Override
    public DataModelMeta getOrNull(@NonNull DataModelDefinition model, String providerKey) {
        return dataModelManagementStore.getOrNull(model.getName(), getName(), normalizeProviderKey(providerKey));
    }

    @Override
    public void set(@NonNull DataModelDefinition model, @NonNull DataModelMeta meta, String providerKey) {
        dataModelManagementStore.set(model.getName(), meta, getName(), normalizeProviderKey(providerKey));
    }

    @Override
    public void clear(@NonNull DataModelDefinition model, String providerKey) {
        dataModelManagementStore.delete(model.getName(), getName(), normalizeProviderKey(providerKey));
    }

    protected String normalizeProviderKey(String providerKey) {
        return providerKey;
    }
}
