package com.ueueo.ddd.domain.entities.events.distributed;

import com.ueueo.ObjectMapping.IObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EtoMappingDictionaryItem {
    private Class<?> etoType;

    private Class<? extends IObjectMapper> objectMappingContextType;

}
