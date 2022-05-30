namespace Volo.Abp.ObjectMapping;

/**
 * Defines a simple interface to automatically map objects.
*/
public interface IObjectMapper
{
    /**
     * Gets the underlying <see cref="IAutoObjectMappingProvider"/> object that is used for auto object mapping.
    */
    IAutoObjectMappingProvider AutoObjectMappingProvider;//  { get; }

    /**
     * Converts an object to another. Creates a new object of <see cref="TDestination"/>.
    *
     * <typeparam name="TDestination">Type of the destination object</typeparam>
     * <typeparam name="TSource">Type of the source object</typeparam>
     * <param name="source">Source object</param>
     */
    TDestination Map<TSource, TDestination>(TSource source);

    /**
     * Execute a mapping from the source object to the existing destination object
    *
     * <typeparam name="TSource">Source type</typeparam>
     * <typeparam name="TDestination">Destination type</typeparam>
     * <param name="source">Source object</param>
     * <param name="destination">Destination object</param>
     * <returns>Returns the same <see cref="destination"/> object after mapping operation</returns>
     */
    TDestination Map<TSource, TDestination>(TSource source, TDestination destination);
}

/**
 * Defines a simple interface to automatically map objects for a specific context.
*/
public interface IObjectMapper<TContext> : IObjectMapper
{

}

/**
 * Maps an object to another.
 * Implement this interface to override object to object mapping for specific types.
*/
 * <typeparam name="TSource"></typeparam>
 * <typeparam name="TDestination"></typeparam>
public interface IObjectMapper<in TSource, TDestination>
{
    /**
     * Converts an object to another. Creates a new object of <see cref="TDestination"/>.
    *
     * <param name="source">Source object</param>
     */
    TDestination Map(TSource source);

    /**
     * Execute a mapping from the source object to the existing destination object
    *
     * <param name="source">Source object</param>
     * <param name="destination">Destination object</param>
     * <returns>Returns the same <see cref="destination"/> object after mapping operation</returns>
     */
    TDestination Map(TSource source, TDestination destination);
}
