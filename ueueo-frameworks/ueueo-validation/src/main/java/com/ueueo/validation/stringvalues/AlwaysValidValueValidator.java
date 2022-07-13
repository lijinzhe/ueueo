package com.ueueo.validation.stringvalues;

/**
 * @author Lee
 * @date 2022-05-29 16:46
 */
@ValueValidatorAttribute(name = "NULL")
public class AlwaysValidValueValidator extends ValueValidatorBase {
    @Override
    public boolean isValid(Object value) {
        return true;
    }
}
