package com.ueueo.datamodels;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:28
 */
public interface IDataModelDefinitionContext {

    DataModelDefinition getOrNull(String name);

    List<DataModelDefinition> getAll();

    void add(DataModelDefinition... definitions);

}
