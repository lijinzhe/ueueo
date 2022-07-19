package com.ueueo.datamodels.meta;

import com.ueueo.datamodels.definition.DataModelDefinition;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public interface IDataModelMetaProvider {
    String getName();

    DataModelMeta getOrNull(@NonNull DataModelDefinition dataModelDefinition);

    List<DataModelMeta> getAll(@NonNull List<DataModelDefinition> dataModelDefinitions);

}
