using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using JetBrains.Annotations;

namespace Volo.Abp.Validation;

public class ObjectValidationContext
{
    [NotNull]
    public Object ValidatingObject;//  { get; }

    public List<ValidationResult> Errors;//  { get; }

    public ObjectValidationContext(@Nonnull Object validatingObject)
    {
        ValidatingObject = Objects.requireNonNull(validatingObject, nameof(validatingObject));
        Errors = new List<ValidationResult>();
    }
}
