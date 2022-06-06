package com.ueueo.ddd.domain.entities.events.distributed;

import com.ueueo.ObjectMapping.IObjectMapper;
import com.ueueo.ddd.domain.entities.IEntity;
import com.ueueo.dynamicproxy.ProxyHelper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityToEtoMapper implements IEntityToEtoMapper {

    protected AbpDistributedEntityEventOptions options;

    public EntityToEtoMapper(AbpDistributedEntityEventOptions options) {
        this.options = options;
    }

    @Override
    public Object map(IEntity entity) {
        Objects.requireNonNull(entity);

        Class<?> entityType = ProxyHelper.unProxy(entity).getClass();
        EtoMappingDictionaryItem etoMappingItem = options.getEtoMappings().get(entityType);
        if (etoMappingItem == null) {
            String keys = entity.getId().toString();
            return new EntityEto(entityType.getName(), keys);
        }

        Class<? extends IObjectMapper> objectMapperType = etoMappingItem.getObjectMappingContextType();
        if (objectMapperType != null) {
            IObjectMapper objectMapper = Mappers.getMapper(objectMapperType);
            if (objectMapper != null) {
                return objectMapper.map(entity);
            }
        }
        Map<String, Object> dest = new HashMap<>();
        BeanUtils.copyProperties(entity, dest);
        return dest;
    }
}
