package com.ueueo.datamodels;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:35
 */
public interface IDataModelStore {

    DataModelMeta getOrNull(String name, String providerName, String providerKey);

    List<DataModelMeta> getAll(List<String> names, String providerName, String providerKey);

}
