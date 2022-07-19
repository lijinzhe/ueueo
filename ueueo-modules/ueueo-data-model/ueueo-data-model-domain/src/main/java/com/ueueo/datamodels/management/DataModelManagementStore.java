package com.ueueo.datamodels.management;

import com.ueueo.KeyValuePair;
import com.ueueo.caching.IDistributedCache;
import com.ueueo.datamodels.definition.DataModelDefinition;
import com.ueueo.datamodels.definition.IDataModelDefinitionManager;
import com.ueueo.datamodels.meta.DataModelMeta;
import com.ueueo.guids.IGuidGenerator;
import com.ueueo.util.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-07-15 09:33
 */
public class DataModelManagementStore implements IDataModelManagementStore {

    private IGuidGenerator guidGenerator;

    private IDataModelRepository dataModelRepository;

    private IDataModelDefinitionManager definitionManager;

    private IDistributedCache<DataModelCacheItem> cache;

    public DataModelManagementStore(IGuidGenerator guidGenerator,
                                    IDataModelRepository dataModelRepository,
                                    IDataModelDefinitionManager definitionManager,
                                    IDistributedCache<DataModelCacheItem> cache) {
        this.guidGenerator = guidGenerator;
        this.dataModelRepository = dataModelRepository;
        this.definitionManager = definitionManager;
        this.cache = cache;
    }

    @Override
    public DataModelMeta getOrNull(String name, String providerName, String providerKey) {
        return getCacheItem(name, providerName, providerKey).getValue().getMeta();
    }

    @Override
    public List<DataModelMeta> getList(String providerName, String providerKey) {
        List<DataModel> dataModels = dataModelRepository.getList(providerName, providerKey);
        return dataModels.stream().map(DataModel::getMeta).collect(Collectors.toList());
    }

    @Override
    public List<DataModelMeta> getList(List<String> names, String providerName, String providerKey) {
        Check.notNullOrEmpty(names, "names");

        List<DataModelMeta> result = new ArrayList<>();

        if (names.size() == 1) {
            String name = names.get(0);
            result.add(getCacheItem(name, providerName, providerKey).getValue().getMeta());
            return result;
        }

        List<KeyValuePair<String, DataModelCacheItem>> cacheItems = getCacheItems(names, providerName, providerKey);
        for (KeyValuePair<String, DataModelCacheItem> item : cacheItems) {
            result.add(item.getValue().getValue().getMeta());
        }

        return result;
    }

    protected List<KeyValuePair<String, DataModelCacheItem>> getCacheItems(List<String> names, String providerName, String providerKey) {
        List<String> cacheKeys = names.stream().map(x -> calculateCacheKey(x, providerName, providerKey)).collect(Collectors.toList());

        List<KeyValuePair<String, DataModelCacheItem>> cacheItems = cache.getMany(cacheKeys, null, true);

        if (cacheItems.stream().allMatch(x -> x.getValue() != null)) {
            return cacheItems;
        }

        List<String> notCacheKeys = cacheItems.stream().filter(x -> x.getValue() == null).map(KeyValuePair::getKey).collect(Collectors.toList());

        List<KeyValuePair<String, DataModelCacheItem>> newCacheItems = setCacheItems(providerName, providerKey, notCacheKeys);

        List<KeyValuePair<String, DataModelCacheItem>> result = new ArrayList<>();
        for (String key : cacheKeys) {
            KeyValuePair<String, DataModelCacheItem> item = newCacheItems.stream().filter(x -> x.getKey().equals(key)).findFirst().orElse(null);
            if (item == null || item.getValue() == null) {
                item = cacheItems.stream().filter(x -> x.getKey().equals(key)).findFirst().orElse(null);
            }
            if (item != null) {
                result.add(new KeyValuePair<>(key, item.getValue()));
            }
        }

        return result;
    }

    private List<KeyValuePair<String, DataModelCacheItem>> setCacheItems(
            String providerName,
            String providerKey,
            List<String> notCacheKeys) {

        List<DataModelDefinition> dataModelDefinitions = definitionManager.getAll()
                .stream()
                .filter(x -> notCacheKeys.stream().anyMatch(k -> getDataModelNameFormCacheKeyOrNull(k).equals(x.getName())))
                .collect(Collectors.toList());

        List<String> noCacheDataModeNames = notCacheKeys.stream().map(this::getDataModelNameFormCacheKeyOrNull).collect(Collectors.toList());
        Map<String, DataModel> dataModelMap = dataModelRepository.getList(noCacheDataModeNames, providerName, providerKey)
                .stream().collect(Collectors.toMap(DataModel::getName, s -> s));

        List<KeyValuePair<String, DataModelCacheItem>> cacheItems = new ArrayList<>();

        for (DataModelDefinition dataModelDefinition : dataModelDefinitions) {
            DataModel dataModel = dataModelMap.get(dataModelDefinition.getName());
            cacheItems.add(
                    new KeyValuePair<>(
                            calculateCacheKey(dataModelDefinition.getName(), providerName, providerKey),
                            new DataModelCacheItem(dataModel)
                    )
            );
        }

        cache.setMany(cacheItems, null, null, true);

        return cacheItems;
    }

    @Override
    public void set(String name, DataModelMeta meta, String providerName, String providerKey) {
        DataModel dataModel = dataModelRepository.find(name, providerName, providerKey);

        if (dataModel == null) {
            dataModel = new DataModel(guidGenerator.create(), name, meta, providerName, providerKey);
            dataModelRepository.insert(dataModel, true);
        } else {
            dataModel.setMeta(meta);
            dataModelRepository.update(dataModel, true);
        }

        cache.set(calculateCacheKey(name, providerName, providerKey), new DataModelCacheItem(dataModel), null, null, true);
    }

    @Override
    public void delete(String name, String providerName, String providerKey) {
        DataModel setting = dataModelRepository.find(name, providerName, providerKey);

        if (setting != null) {
            dataModelRepository.delete(setting, true);
            cache.remove(calculateCacheKey(name, providerName, providerKey), null, true);
        }
    }

    protected DataModelCacheItem getCacheItem(String name, String providerName, String providerKey) {
        String cacheKey = calculateCacheKey(name, providerName, providerKey);
        DataModelCacheItem cacheItem = cache.get(cacheKey, false, true);

        if (cacheItem != null) {
            return cacheItem;
        }

        cacheItem = new DataModelCacheItem(null);

        setCacheItems(providerName, providerKey, name, cacheItem);

        return cacheItem;
    }

    private void setCacheItems(
            String providerName,
            String providerKey,
            String currentName,
            DataModelCacheItem currentCacheItem) {
        List<DataModelDefinition> definitions = definitionManager.getAll();
        Map<String, DataModel> modelMap = dataModelRepository.getList(providerName, providerKey)
                .stream().collect(Collectors.toMap(DataModel::getName, o -> o, (o, o2) -> o));

        List<KeyValuePair<String, DataModelCacheItem>> cacheItems = new ArrayList<>();

        for (DataModelDefinition definition : definitions) {
            DataModel dataModel = modelMap.get(definition.getName());

            cacheItems.add(
                    new KeyValuePair<>(
                            calculateCacheKey(definition.getName(), providerName, providerKey),
                            new DataModelCacheItem(dataModel)
                    )
            );

            if (definition.getName().equals(currentName)) {
                currentCacheItem.setValue(dataModel);
            }
        }

        cache.setMany(cacheItems, null, null, true);
    }

    protected String calculateCacheKey(String name, String providerName, String providerKey) {
        return DataModelCacheItem.calculateCacheKey(name, providerName, providerKey);
    }

    protected String getDataModelNameFormCacheKeyOrNull(String key) {
        return DataModelCacheItem.getDataModelNameFormCacheKeyOrNull(key);
    }
}
