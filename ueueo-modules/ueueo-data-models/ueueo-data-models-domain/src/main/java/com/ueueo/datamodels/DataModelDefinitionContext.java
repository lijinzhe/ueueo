package com.ueueo.datamodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2021-08-18 20:59
 */
public class DataModelDefinitionContext implements IDataModelDefinitionContext {

    protected Map<String, DataModelDefinition> dataModels;

    public DataModelDefinitionContext(Map<String, DataModelDefinition> dataModels) {
        this.dataModels = dataModels;
    }

    @Override
    public DataModelDefinition getOrNull(String name) {
        return dataModels.get(name);
    }

    @Override
    public List<DataModelDefinition> getAll() {
        return new ArrayList<>(dataModels.values());
    }

    @Override
    public void add(DataModelDefinition... definitions) {
        if (definitions != null) {
            for (DataModelDefinition definition : definitions) {
                dataModels.put(definition.getName(), definition);
            }
        }
    }
}
