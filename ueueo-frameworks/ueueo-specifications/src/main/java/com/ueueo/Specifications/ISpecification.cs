using System;
using System.Linq.Expressions;

namespace Volo.Abp.Specifications;

/**
 * Represents that the implemented classes are specifications. For more
 * information about the specification pattern, please refer to
 * http://martinfowler.com/apsupp/spec.pdf.
*/
 * <typeparam name="T">The type of the Object to which the specification is applied.</typeparam>
public interface ISpecification<T>
{
    /**
     * Returns a <see cref="bool"/> value which indicates whether the specification
     * is satisfied by the given object.
    *
     * <param name="obj">The object to which the specification is applied.</param>
     * <returns>True if the specification is satisfied, otherwise false.</returns>
     */
    boolean IsSatisfiedBy(T obj);

    /**
     * Gets the LINQ expression which represents the current specification.
    */
     * <returns>The LINQ expression.</returns>
    Expression<Func<T, bool>> ToExpression();
}
