using System;
using System.Linq.Expressions;

namespace Volo.Abp.Specifications;

/**
 * Represents the base class for specifications.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public abstract class Specification<T> : ISpecification<T>
{
    /**
     * Returns a <see cref="bool"/> value which indicates whether the specification
     * is satisfied by the given object.
    *
     * <param name="obj">The object to which the specification is applied.</param>
     * <returns>True if the specification is satisfied, otherwise false.</returns>
     */
    public   boolean IsSatisfiedBy(T obj)
    {
        return ToExpression().Compile()(obj);
    }

    /**
     * Gets the LINQ expression which represents the current specification.
    */
     * <returns>The LINQ expression.</returns>
    public abstract Expression<Func<T, bool>> ToExpression();

    /**
     * Implicitly converts a specification to expression.
    *
     * <param name="specification"></param>
     */
    public static implicit operator Expression<Func<T, bool>>(Specification<T> specification)
    {
        return specification.ToExpression();
    }
}
