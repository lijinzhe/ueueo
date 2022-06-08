package com.ueueo.ObjectMapping;

public interface IAutoObjectMappingProvider {

    <TSource, TDestination> TDestination map(TSource source, Class<? extends TDestination> destinationType);

    <TSource, TDestination> TDestination map(TSource source, TDestination destination);
}
