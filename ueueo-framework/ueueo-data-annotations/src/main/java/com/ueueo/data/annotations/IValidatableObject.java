package com.ueueo.data.annotations;

import java.util.Collection;

/**
 * @author Lee
 * @date 2022-05-25 15:26
 */
public interface IValidatableObject {
    Collection<ValidationResult> validate(ValidationContext validationContext);
}
