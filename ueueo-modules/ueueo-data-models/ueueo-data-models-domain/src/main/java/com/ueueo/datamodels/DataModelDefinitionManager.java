package com.ueueo.datamodels;

import com.ueueo.exception.BaseException;
import com.ueueo.util.Check;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
@Getter
public class DataModelDefinitionManager implements IDataModelDefinitionManager {

    protected final Map<String, DataModelDefinition> dataModelDefinitions;

    private final List<IDataModelDefinitionProvider> definitionProviders;

    public DataModelDefinitionManager(List<IDataModelDefinitionProvider> definitionProviders) {
        this.definitionProviders = definitionProviders;
        this.dataModelDefinitions = new HashMap<>(createDataModelDefinitions());
    }

    @NonNull
    @Override
    public DataModelDefinition get(@NonNull String name) {
        Check.notNullOrEmpty(name, "name");
        DataModelDefinition setting = getOrNull(name);
        if (setting == null) {
            throw new BaseException("Undefined setting: " + name);
        }
        return setting;
    }

    @Override
    public List<DataModelDefinition> getAll() {
        return new ArrayList<>(dataModelDefinitions.values());
    }

    @Override
    public DataModelDefinition getOrNull(String name) {
        return dataModelDefinitions.get(name);
    }

    protected Map<String, DataModelDefinition> createDataModelDefinitions() {
        Map<String, DataModelDefinition> dataModels = new HashMap<>();
        for (IDataModelDefinitionProvider provider : definitionProviders) {
            provider.define(new DataModelDefinitionContext(dataModels));
        }
        return dataModels;
    }
}
