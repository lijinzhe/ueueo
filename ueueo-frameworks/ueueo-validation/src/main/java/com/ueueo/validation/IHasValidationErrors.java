package com.ueueo.validation;

import java.util.Collection;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-25 22:10
 */
public interface IHasValidationErrors {
    Collection<ValidationResult> getValidationErrors();
}
