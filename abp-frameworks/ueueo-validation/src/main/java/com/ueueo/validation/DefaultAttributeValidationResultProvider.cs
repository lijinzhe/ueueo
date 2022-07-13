using System.ComponentModel.DataAnnotations;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Validation;

public class DefaultAttributeValidationResultProvider : IAttributeValidationResultProvider, ITransientDependency
{
    public   ValidationResult GetOrDefault(ValidationAttribute validationAttribute, Object validatingObject, ValidationContext validationContext)
    {
        return validationAttribute.GetValidationResult(validatingObject, validationContext);
    }
}
