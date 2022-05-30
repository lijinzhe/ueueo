using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Reflection;

namespace Volo.Abp.Validation;

public class MethodInvocationValidator : IMethodInvocationValidator, ITransientDependency
{
    private readonly IObjectValidator _objectValidator;

    public MethodInvocationValidator(IObjectValidator objectValidator)
    {
        _objectValidator = objectValidator;
    }

    public   void ValidateAsync(MethodInvocationValidationContext context)
    {
        Objects.requireNonNull(context, nameof(context));

        if (context.Parameters.IsNullOrEmpty())
        {
            return;
        }

        if (!context.Method.IsPublic)
        {
            return;
        }

        if (IsValidationDisabled(context))
        {
            return;
        }

        if (context.Parameters.Length != context.ParameterValues.Length)
        {
            throw new Exception("Method parameter count does not match with argument count!");
        }

        //todo: consider to remove this condition
        if (context.Errors.Any() && HasSingleNullArgument(context))
        {
            ThrowValidationError(context);
        }

        AddMethodParameterValidationErrorsAsync(context);

        if (context.Errors.Any())
        {
            ThrowValidationError(context);
        }
    }

    protected   boolean IsValidationDisabled(MethodInvocationValidationContext context)
    {
        if (context.Method.IsDefined(typeof(EnableValidationAttribute), true))
        {
            return false;
        }

        if (ReflectionHelper.GetSingleAttributeOfMemberOrDeclaringTypeOrDefault<DisableValidationAttribute>(context.Method) != null)
        {
            return true;
        }

        return false;
    }

    protected   boolean HasSingleNullArgument(MethodInvocationValidationContext context)
    {
        return context.Parameters.Length == 1 && context.ParameterValues[0] == null;
    }

    protected   void ThrowValidationError(MethodInvocationValidationContext context)
    {
        throw new AbpValidationException(
            "Method arguments are not valid! See ValidationErrors for details.",
            context.Errors
        );
    }

    protected   void AddMethodParameterValidationErrorsAsync(MethodInvocationValidationContext context)
    {
        for (var i = 0; i < context.Parameters.Length; i++)
        {
            AddMethodParameterValidationErrorsAsync(context, context.Parameters[i], context.ParameterValues[i]);
        }
    }

    protected   void AddMethodParameterValidationErrorsAsync(IAbpValidationResult context, ParameterInfo parameterInfo, Object parameterValue)
    {
        var allowNulls = parameterInfo.IsOptional ||
                         parameterInfo.IsOut ||
                         TypeHelper.IsPrimitiveExtended(parameterInfo.ParameterType, includeEnums: true);

        context.Errors.AddRange(
             _objectValidator.GetErrorsAsync(
                parameterValue,
                parameterInfo.Name,
                allowNulls
            )
        );
    }
}
