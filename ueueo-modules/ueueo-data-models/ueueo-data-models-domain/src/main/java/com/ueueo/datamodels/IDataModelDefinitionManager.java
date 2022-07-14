package com.ueueo.datamodels;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:32
 */
public interface IDataModelDefinitionManager {
    @NonNull
    DataModelDefinition get(@NonNull String name);

    List<DataModelDefinition> getAll();

    DataModelDefinition getOrNull(String name);
}
