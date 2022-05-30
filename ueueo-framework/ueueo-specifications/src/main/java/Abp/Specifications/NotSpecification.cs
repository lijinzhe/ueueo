using System;
using System.Linq.Expressions;

namespace Volo.Abp.Specifications;

/**
 * Represents the specification which indicates the semantics opposite to the given specification.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public class NotSpecification<T> : Specification<T>
{
    private readonly ISpecification<T> _specification;

    /**
     * Initializes a new instance of <see cref="NotSpecification{T}"/> class.
    *
     * <param name="specification">The specification to be reversed.</param>
     */
    public NotSpecification(ISpecification<T> specification)
    {
        _specification = specification;
    }

    /**
     * Gets the LINQ expression which represents the current specification.
    *
     * <returns>The LINQ expression.</returns>
     */
    @Override
    public Expression<Func<T, bool>> ToExpression()
    {
        var expression = _specification.ToExpression();
        return Expression.Lambda<Func<T, bool>>(
            Expression.Not(expression.Body),
            expression.Parameters
        );
    }
}
