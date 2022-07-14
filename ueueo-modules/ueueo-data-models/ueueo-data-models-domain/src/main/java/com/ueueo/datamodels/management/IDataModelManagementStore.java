package com.ueueo.datamodels.management;

import com.ueueo.datamodels.DataModelMeta;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:45
 */
public interface IDataModelManagementStore {

    DataModelMeta getOrNull(String name, String providerName, String providerKey);

    List<DataModelMeta> getList(String providerName, String providerKey);

    List<DataModelMeta> getList(String[] names, String providerName, String providerKey);

    void set(String name, DataModelMeta value, String providerName, String providerKey);

    void delete(String name, String providerName, String providerKey);
}
