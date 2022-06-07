namespace Volo.Abp.Specifications;

/**
 * Represents the base class for composite specifications.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public abstract class CompositeSpecification<T> : Specification<T>, ICompositeSpecification<T>
{
    /**
     * Constructs a new instance of <see cref="CompositeSpecification{T}"/> class.
    *
     * <param name="left">The first specification.</param>
     * <param name="right">The second specification.</param>
     */
    protected CompositeSpecification(ISpecification<T> left, ISpecification<T> right)
    {
        Left = left;
        Right = right;
    }

    /**
     * Gets the first specification.
    */
    public ISpecification<T> Left;//  { get; }

    /**
     * Gets the second specification.
    */
    public ISpecification<T> Right;//  { get; }
}
