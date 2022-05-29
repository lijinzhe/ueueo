package com.ueueo.validation;

import com.ueueo.data.annotations.ValidationAttribute;
import com.ueueo.data.annotations.ValidationContext;
import com.ueueo.data.annotations.ValidationResult;

/**
 * @author Lee
 * @date 2022-05-29 17:13
 */
public interface IAttributeValidationResultProvider {
    ValidationResult getOrDefault(ValidationAttribute validationAttribute,
                                  Object validatingObject,
                                  ValidationContext validationContext);
}
