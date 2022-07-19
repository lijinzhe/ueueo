package com.ueueo.datamodels.meta;

import com.ueueo.datamodels.IDataModelStore;
import com.ueueo.datamodels.definition.DataModelDefinition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用模型元数据Provider
 *
 * @author Lee
 * @date 2022-07-14 17:38
 */
public class GenericDataModelMetaProvider extends DataModelMetaProvider {

    public static final String PROVIDER_NAME = "Generic";

    public GenericDataModelMetaProvider(IDataModelStore dataModelStore) {
        super(dataModelStore);
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public DataModelMeta getOrNull(DataModelDefinition dataModelDefinition) {
        return dataModelStore.getOrNull(dataModelDefinition.getName(), getName(), "");
    }

    @Override
    public List<DataModelMeta> getAll(List<DataModelDefinition> dataModelDefinitions) {
        List<String> names = dataModelDefinitions.stream().map(DataModelDefinition::getName).collect(Collectors.toList());
        return dataModelStore.getAll(names, getName(), "");
    }
}
