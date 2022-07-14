package com.ueueo.datamodels;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public interface IDataModelStore {

    DataModelMeta getOrNull(String name, String providerName, String providerKey);

    List<DataModelMeta> getAll(List<String> names, String providerName, String providerKey);

}
