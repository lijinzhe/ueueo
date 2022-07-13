package com.ueueo.validation.stringvalues;

/**
 * @author Lee
 * @date 2022-05-29 16:47
 */
@ValueValidatorAttribute(name = "BOOLEAN")
public class BooleanValueValidator extends ValueValidatorBase {
    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Boolean) {
            return true;
        }

        try {
            return Boolean.parseBoolean(value.toString());
        } catch (Exception e) {
            return false;
        }
    }
}
