package com.ueueo.datamodels;

import com.ueueo.datamodels.management.IDataModelManagementStore;
import com.ueueo.datamodels.meta.DataModelMeta;
import com.ueueo.datamodels.IDataModelStore;

import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-07-15 11:40
 */
public class DataModelStore implements IDataModelStore {

    private final IDataModelManagementStore dataModelManagementStore;

    public DataModelStore(IDataModelManagementStore dataModelManagementStore) {
        this.dataModelManagementStore = dataModelManagementStore;
    }

    @Override
    public DataModelMeta getOrNull(String name, String providerName, String providerKey) {
        return dataModelManagementStore.getOrNull(name, providerName, providerKey);
    }

    @Override
    public List<DataModelMeta> getAll(List<String> names, String providerName, String providerKey) {
        return dataModelManagementStore.getList(names, providerName, providerKey);
    }
}
