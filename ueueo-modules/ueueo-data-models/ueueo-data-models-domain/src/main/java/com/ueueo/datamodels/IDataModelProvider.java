package com.ueueo.datamodels;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public interface IDataModelProvider {

    DataModelMeta getOrNull(String name);

    List<DataModelMeta> getAll(@NonNull List<String> names);

    List<DataModelMeta> getAll();

}

