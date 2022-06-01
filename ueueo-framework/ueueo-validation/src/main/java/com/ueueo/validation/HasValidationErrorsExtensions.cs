using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using JetBrains.Annotations;

namespace Volo.Abp.Validation;

public static class HasValidationErrorsExtensions
{
    public static TException WithValidationError<TException>(@NonNull this TException exception, @NonNull ValidationResult validationError)
        //where TException : IHasValidationErrors
    {
        Objects.requireNonNull(exception, nameof(exception));
        Objects.requireNonNull(validationError, nameof(validationError));

        exception.ValidationErrors.Add(validationError);

        return exception;
    }

    public static TException WithValidationError<TException>(@NonNull this TException exception, String errorMessage, params String[] memberNames)
        //where TException : IHasValidationErrors
    {
        var validationResult = memberNames.IsNullOrEmpty()
            ? new ValidationResult(errorMessage)
            : new ValidationResult(errorMessage, memberNames);

        return exception.WithValidationError(validationResult);
    }
}
