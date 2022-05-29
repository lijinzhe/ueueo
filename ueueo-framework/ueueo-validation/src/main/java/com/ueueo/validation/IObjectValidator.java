package com.ueueo.validation;

import com.ueueo.data.annotations.ValidationResult;

import java.util.List;

/**
 * @author Lee
 * @date 2022-05-29 17:11
 */
public interface IObjectValidator {

    void validate(Object validatingObject, String name, boolean allowNull);

    List<ValidationResult> getErrors(Object validatingObject, String name, boolean allowNull);
}
