package com.ueueo.validation.stringvalues;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-29 16:56
 */
@Data
@ValueValidatorAttribute(name = "NUMERIC")
public class NumericValueValidator extends ValueValidatorBase {

    private int minValue;
    private int maxValue;

    public NumericValueValidator() {
        minValue = Integer.MIN_VALUE;
        maxValue = Integer.MAX_VALUE;
    }

    public NumericValueValidator(Integer minValue, Integer maxValue) {
        this.minValue = minValue != null ? minValue : Integer.MIN_VALUE;
        this.maxValue = maxValue != null ? maxValue : Integer.MAX_VALUE;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Integer) {
            return isValidInternal((int) value);
        }

        if (value instanceof String) {
            int intValue = 0;
            try {
                intValue = Integer.parseInt(value.toString());
                return isValidInternal(intValue);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    protected boolean isValidInternal(int value) {
        return value >= minValue && value <= maxValue;
    }
}
