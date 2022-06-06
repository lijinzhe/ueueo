using System;
using System.Linq.Expressions;

namespace Volo.Abp.Specifications;

/**
 * Represents the specification which is represented by the corresponding
 * LINQ expression.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public class ExpressionSpecification<T> : Specification<T>
{
    private readonly Expression<Func<T, bool>> _expression;

    /**
     * Initializes a new instance of <c>ExpressionSpecification&lt;T&gt;</c> class.
    *
     * <param name="expression">The LINQ expression which represents the current
     * specification.</param>
     */
    public ExpressionSpecification(Expression<Func<T, bool>> expression)
    {
        _expression = expression;
    }

    /**
     * Gets the LINQ expression which represents the current specification.
    */
     * <returns>The LINQ expression.</returns>
    @Override
    public Expression<Func<T, bool>> ToExpression()
    {
        return _expression;
    }
}
