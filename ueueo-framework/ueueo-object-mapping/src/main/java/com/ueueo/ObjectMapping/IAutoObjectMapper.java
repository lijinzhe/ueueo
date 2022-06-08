package com.ueueo.ObjectMapping;

/**
 * Defines a simple interface to automatically map objects.
 *
 * @author Lee
 * @date 2022-06-08 11:43
 */
public interface IAutoObjectMapper {
    /**
     * Gets the underlying <see cref="IAutoObjectMappingProvider"/> object that is used for auto object mapping.
     */
    IAutoObjectMappingProvider getAutoObjectMappingProvider();

    /**
     * Converts an object to another. Creates a new object of <see cref="TDestination"/>.
     *
     * <typeparam name="TDestination">Type of the destination object</typeparam>
     * <typeparam name="TSource">Type of the source object</typeparam>
     * <param name="source">Source object</param>
     */
    <TSource, TDestination> TDestination map(TSource source, Class<? extends TDestination> destinationType);

    /**
     * Execute a mapping from the source object to the existing destination object
     * </summary>
     * <param name="source">Source object</param>
     * <param name="destination">Destination object</param>
     * <returns>Returns the same <see cref="destination"/> object after mapping operation</returns>
     */
    <TSource, TDestination> TDestination map(TSource source, TDestination destination);
}
