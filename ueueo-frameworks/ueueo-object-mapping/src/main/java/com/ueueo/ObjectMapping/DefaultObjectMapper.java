package com.ueueo.ObjectMapping;

/**
 * @author Lee
 * @date 2022-06-08 13:52
 */
public class DefaultObjectMapper implements IAutoObjectMapper {

    @Override
    public IAutoObjectMappingProvider getAutoObjectMappingProvider() {
        return new BeanUtilsObjectMappingProvider();
    }

    @Override
    public <TSource, TDestination> TDestination map(TSource source, Class<? extends TDestination> destinationType) {
        return getAutoObjectMappingProvider().map(source, destinationType);
    }

    @Override
    public <TSource, TDestination> TDestination map(TSource source, TDestination destination) {
        return getAutoObjectMappingProvider().map(source, destination);
    }
}
