package com.ueueo.ObjectMapping;

import org.springframework.beans.BeanUtils;

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

    /**
     * Execute a mapping from the source object to the existing destination object
     * </summary>
     * <param name="source">Source object</param>
     * <param name="destination">Destination object</param>
     * <returns>Returns the same <see cref="destination"/> object after mapping operation</returns>
     */
    default TDestination map(TSource source, TDestination destination) {
        TDestination dest = map(source);
        BeanUtils.copyProperties(dest, destination);
        return destination;
    }

}

