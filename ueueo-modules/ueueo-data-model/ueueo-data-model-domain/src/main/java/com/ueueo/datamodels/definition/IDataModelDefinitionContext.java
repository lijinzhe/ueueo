package com.ueueo.datamodels.definition;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public interface IDataModelDefinitionContext {

    DataModelDefinition getOrNull(String name);

    List<DataModelDefinition> getAll();

    void add(DataModelDefinition... definitions);

}
