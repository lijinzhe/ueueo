package com.ueueo.datamodels;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public class InMemoryDataModelStore implements IDataModelStore {

    private final ConcurrentHashMap<String, DataModelMeta> META_IN_MEMORY = new ConcurrentHashMap<>();

    @Override
    public DataModelMeta getOrNull(String name, String providerName, String providerKey) {
        String key = String.format("%s:%s:%s",
                StringUtils.trimToEmpty(name),
                StringUtils.trimToEmpty(providerName),
                StringUtils.trimToEmpty(providerKey)
        );
        return META_IN_MEMORY.get(key);
    }

    @Override
    public List<DataModelMeta> getAll(List<String> names, String providerName, String providerKey) {
        return names.stream().map(name -> getOrNull(name, providerName, providerKey)).collect(Collectors.toList());
    }

    public void addDataModelMeta(String name, String providerName, String providerKey, DataModelMeta meta) {
        String key = String.format("%s:%s:%s",
                StringUtils.trimToEmpty(name),
                StringUtils.trimToEmpty(providerName),
                StringUtils.trimToEmpty(providerKey)
        );
        META_IN_MEMORY.put(key, meta);
    }
}
