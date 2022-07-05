package com.ueueo.validation;

import java.util.List;

/**
 *
 * @author Lee
 * @date 2022-05-29 17:15
 */
public interface IAbpValidationResult {
    List<ValidationResult> getErrors();
}
