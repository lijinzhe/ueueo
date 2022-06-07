package com.ueueo.ObjectMapping;

/**
 * Defines a simple interface to automatically map objects.
 */
public interface IObjectMapper<TSource, TDestination> {

    /**
     * Converts an object to another. Creates a new object of <see cref="TDestination"/>.
     *
     * <typeparam name="TDestination">Type of the destination object</typeparam>
     * <typeparam name="TSource">Type of the source object</typeparam>
     * <param name="source">Source object</param>
     */
    TDestination map(TSource source);
}

