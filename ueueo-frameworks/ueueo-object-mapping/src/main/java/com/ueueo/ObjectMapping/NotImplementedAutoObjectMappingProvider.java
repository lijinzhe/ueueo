package com.ueueo.ObjectMapping;

import com.ueueo.exception.BaseException;

public class NotImplementedAutoObjectMappingProvider implements IAutoObjectMappingProvider {

    @Override
    public <TSource, TDestination> TDestination map(TSource tSource, Class<? extends TDestination> destinationType) {
        throw new BaseException("Can not map from given object  to destination type.");
    }

    @Override
    public <TSource, TDestination> TDestination map(TSource tSource, TDestination tDestination) {
        throw new BaseException("Can not map from given object  to destination type.");
    }

}
