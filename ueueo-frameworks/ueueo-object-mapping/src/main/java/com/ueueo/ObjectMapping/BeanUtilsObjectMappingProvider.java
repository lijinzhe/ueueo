package com.ueueo.ObjectMapping;

import org.springframework.beans.BeanUtils;

/**
 * @author Lee
 * @date 2022-06-08 13:53
 */
public class BeanUtilsObjectMappingProvider implements IAutoObjectMappingProvider {
    @Override
    public <TSource, TDestination> TDestination map(TSource source, Class<? extends TDestination> destinationType) {
        TDestination destination = BeanUtils.instantiateClass(destinationType);
        BeanUtils.copyProperties(source, destination);
        return destination;
    }

    @Override
    public <TSource, TDestination> TDestination map(TSource source, TDestination destination) {
        BeanUtils.copyProperties(source, destination);
        return destination;
    }
}
