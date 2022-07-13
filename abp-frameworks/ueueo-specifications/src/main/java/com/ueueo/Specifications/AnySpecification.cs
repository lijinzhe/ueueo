using System;
using System.Linq.Expressions;

namespace Volo.Abp.Specifications;

/**
 * Represents the specification that can be satisfied by the given object
 * in any circumstance.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public sealed class AnySpecification<T> : Specification<T>
{
    /**
     * Gets the LINQ expression which represents the current specification.
    */
     * <returns>The LINQ expression.</returns>
    @Override
    public Expression<Func<T, bool>> ToExpression()
    {
        return o => true;
    }
}
