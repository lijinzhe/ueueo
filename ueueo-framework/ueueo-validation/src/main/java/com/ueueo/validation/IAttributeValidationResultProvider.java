package com.ueueo.validation;

import com.ueueo.validation.annotations.ValidationAttribute;

/**
 * @author Lee
 * @date 2022-05-29 17:13
 */
public interface IAttributeValidationResultProvider {
    ValidationResult getOrDefault(ValidationAttribute validationAttribute,
                                  Object validatingObject,
                                  ValidationContext validationContext);
}
