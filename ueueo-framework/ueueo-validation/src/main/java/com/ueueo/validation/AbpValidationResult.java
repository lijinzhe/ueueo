package com.ueueo.validation;

import com.ueueo.data.annotations.ValidationResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-29 17:17
 */
@Getter
public class AbpValidationResult implements IAbpValidationResult {

    private List<ValidationResult> errors;

    public AbpValidationResult() {
        errors = new ArrayList<>();
    }
}
