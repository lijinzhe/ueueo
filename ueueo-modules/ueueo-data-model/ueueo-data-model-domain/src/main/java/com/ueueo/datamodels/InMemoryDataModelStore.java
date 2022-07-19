package com.ueueo.datamodels;

import com.ueueo.datamodels.meta.DataModelMeta;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public class InMemoryDataModelStore implements IDataModelStore {

    private static final String CACHE_KEY_FORMAT = "pn:%s,pk:%s,n:%s";

    private final ConcurrentHashMap<String, DataModelMeta> META_IN_MEMORY = new ConcurrentHashMap<>();

    @Override
    public DataModelMeta getOrNull(String name, String providerName, String providerKey) {
        return META_IN_MEMORY.get(calculateKey(name, providerName, providerKey));
    }

    @Override
    public List<DataModelMeta> getAll(List<String> names, String providerName, String providerKey) {
        return names.stream().map(name -> getOrNull(name, providerName, providerKey)).collect(Collectors.toList());
    }

    public void addDataModel(String name, String providerName, String providerKey, DataModelMeta meta) {
        META_IN_MEMORY.put(calculateKey(name, providerName, providerKey), meta);
    }

    protected String calculateKey(String name, String providerName, String providerKey) {
        return String.format(CACHE_KEY_FORMAT, providerName, providerKey, name);
    }
}
