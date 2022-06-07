using JetBrains.Annotations;

namespace Volo.Abp.Specifications;

public static class SpecificationExtensions
{
    /**
     * Combines the current specification instance with another specification instance
     * and returns the combined specification which represents that both the current and
     * the given specification must be satisfied by the given object.
    *
     * <param name="specification">The specification</param>
     * <param name="other">The specification instance with which the current specification is combined.</param>
     * <returns>The combined specification instance.</returns>
     */
    public static ISpecification<T> And<T>(@NonNull this ISpecification<T> specification,
        @NonNull ISpecification<T> other)
    {
        Objects.requireNonNull(specification, nameof(specification));
        Objects.requireNonNull(other, nameof(other));

        return new AndSpecification<T>(specification, other);
    }

    /**
     * Combines the current specification instance with another specification instance
     * and returns the combined specification which represents that either the current or
     * the given specification should be satisfied by the given object.
    *
     * <param name="specification">The specification</param>
     * <param name="other">The specification instance with which the current specification
     * is combined.</param>
     * <returns>The combined specification instance.</returns>
     */
    public static ISpecification<T> Or<T>(@NonNull this ISpecification<T> specification,
        @NonNull ISpecification<T> other)
    {
        Objects.requireNonNull(specification, nameof(specification));
        Objects.requireNonNull(other, nameof(other));

        return new OrSpecification<T>(specification, other);
    }

    /**
     * Combines the current specification instance with another specification instance
     * and returns the combined specification which represents that the current specification
     * should be satisfied by the given object but the specified specification should not.
    *
     * <param name="specification">The specification</param>
     * <param name="other">The specification instance with which the current specification
     * is combined.</param>
     * <returns>The combined specification instance.</returns>
     */
    public static ISpecification<T> AndNot<T>(@NonNull this ISpecification<T> specification,
        @NonNull ISpecification<T> other)
    {
        Objects.requireNonNull(specification, nameof(specification));
        Objects.requireNonNull(other, nameof(other));

        return new AndNotSpecification<T>(specification, other);
    }

    /**
     * Reverses the current specification instance and returns a specification which represents
     * the semantics opposite to the current specification.
    */
     * <returns>The reversed specification instance.</returns>
    public static ISpecification<T> Not<T>(@NonNull this ISpecification<T> specification)
    {
        Objects.requireNonNull(specification, nameof(specification));

        return new NotSpecification<T>(specification);
    }
}
