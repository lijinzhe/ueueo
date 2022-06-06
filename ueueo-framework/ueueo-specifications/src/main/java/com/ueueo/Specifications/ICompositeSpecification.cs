namespace Volo.Abp.Specifications;

/**
 * Represents that the implemented classes are composite specifications.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public interface ICompositeSpecification<T> : ISpecification<T>
{
    /**
     * Gets the left side of the specification.
    */
    ISpecification<T> Left;//  { get; }

    /**
     * Gets the right side of the specification.
    */
    ISpecification<T> Right;//  { get; }
}
