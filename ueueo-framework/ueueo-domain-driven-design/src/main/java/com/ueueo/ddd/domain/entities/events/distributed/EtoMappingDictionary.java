package com.ueueo.ddd.domain.entities.events.distributed;

import java.util.HashMap;

public class EtoMappingDictionary extends HashMap<Class<?>, EtoMappingDictionaryItem> {

    public void add(Class<?> entityType, Class<?> entityEtoType, Class<?> objectMappingContextType) {
        put(entityType, new EtoMappingDictionaryItem(entityEtoType, objectMappingContextType));
    }
}
