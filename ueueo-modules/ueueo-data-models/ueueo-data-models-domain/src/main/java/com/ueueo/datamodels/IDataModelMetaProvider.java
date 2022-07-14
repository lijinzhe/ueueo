package com.ueueo.datamodels;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-18 20:36
 */
public interface IDataModelMetaProvider {
    String getName();

    DataModelMeta getOrNull(@NonNull DataModelDefinition dataModelDefinition);

    List<DataModelMeta> getAll(@NonNull List<DataModelDefinition> dataModelDefinitions);

}
