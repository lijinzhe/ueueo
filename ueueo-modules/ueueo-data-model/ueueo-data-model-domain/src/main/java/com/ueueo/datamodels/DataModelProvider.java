package com.ueueo.datamodels;

import com.ueueo.datamodels.definition.DataModelDefinition;
import com.ueueo.datamodels.definition.IDataModelDefinitionManager;
import com.ueueo.datamodels.meta.DataModelMeta;
import com.ueueo.datamodels.meta.IDataModelMetaProvider;
import com.ueueo.datamodels.meta.IDataModelMetaProviderManager;
import com.ueueo.exception.BaseException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public class DataModelProvider implements IDataModelProvider {

    protected IDataModelDefinitionManager definitionManager;
    protected IDataModelMetaProviderManager metaProviderManager;

    public DataModelProvider(IDataModelDefinitionManager definitionManager,
                             IDataModelMetaProviderManager metaProviderManager) {
        this.definitionManager = definitionManager;
        this.metaProviderManager = metaProviderManager;
    }

    @Override
    public DataModelMeta getOrNull(String name) {
        DataModelDefinition definition = definitionManager.get(name);
        List<IDataModelMetaProvider> providers = metaProviderManager.getProviders();

        if (!definition.getProviders().isEmpty()) {
            providers = providers.stream().filter(p -> definition.getProviders().contains(p.getName())).collect(Collectors.toList());
        }

        return getOrNullValueFromProviders(providers, definition);
    }

    @Override
    public List<DataModelMeta> getAll(List<String> names) {
        Set<String> nameSet = new HashSet<>(names);
        Map<String, DataModelMeta> result = new HashMap<>();
        List<DataModelDefinition> definitions = definitionManager.getAll()
                .stream().filter(def -> nameSet.contains(def.getName()))
                .collect(Collectors.toList());

        for (IDataModelMetaProvider provider : metaProviderManager.getProviders()) {
            List<DataModelMeta> dataModelMetas = provider.getAll(definitions.stream()
                    .filter(def -> def.getProviders().isEmpty() || def.getProviders().contains(provider.getName()))
                    .collect(Collectors.toList())
            );
            List<DataModelMeta> notNullValues = dataModelMetas.stream().filter(val -> CollectionUtils.isNotEmpty(val.getFields())).collect(Collectors.toList());
            for (DataModelMeta dataModelMeta : notNullValues) {
                DataModelDefinition dataModelDefinition = definitions.stream().filter(def -> def.getName().equals(dataModelMeta.getName())).findFirst().orElse(null);
                if (dataModelDefinition != null) {
                    result.putIfAbsent(dataModelMeta.getName(), dataModelMeta);
                }
            }
            definitions.removeIf(def -> notNullValues.stream().anyMatch(val -> val.getName().equals(def.getName())));
        }
        return new ArrayList<>(result.values());
    }

    @Override
    public List<DataModelMeta> getAll() throws BaseException {
        List<DataModelMeta> metaValues = new ArrayList<>();
        List<DataModelDefinition> definitions = definitionManager.getAll();
        for (DataModelDefinition definition : definitions) {
            metaValues.add(getOrNull(definition.getName()));
        }
        return metaValues;
    }

    protected DataModelMeta getOrNullValueFromProviders(List<IDataModelMetaProvider> providers, DataModelDefinition setting) {
        for (IDataModelMetaProvider provider : providers) {
            DataModelMeta meta = provider.getOrNull(setting);
            if (meta != null) {
                return meta;
            }
        }
        return null;
    }

}
