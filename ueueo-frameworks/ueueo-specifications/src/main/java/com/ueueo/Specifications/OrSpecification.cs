using System;
using System.Linq.Expressions;

namespace Volo.Abp.Specifications;

/**
 * Represents the combined specification which indicates that either of the given
 * specification should be satisfied by the given object.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public class OrSpecification<T> : CompositeSpecification<T>
{
    /**
     * Initializes a new instance of <see cref="OrSpecification{T}"/> class.
    *
     * <param name="left">The first specification.</param>
     * <param name="right">The second specification.</param>
     */
    public OrSpecification(ISpecification<T> left, ISpecification<T> right) : base(left, right)
    {
    }

    /**
     * Gets the LINQ expression which represents the current specification.
    *
     * <returns>The LINQ expression.</returns>
     */
    @Override
    public Expression<Func<T, bool>> ToExpression()
    {
        return Left.ToExpression().Or(Right.ToExpression());
    }
}
