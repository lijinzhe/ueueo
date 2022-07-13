package com.ueueo.validation.stringvalues;

/**
 * @author Lee
 * @date 2022-05-29 17:01
 */
@ValueValidatorAttribute(name = "STRING")
public class StringValueValidator extends ValueValidatorBase {
    private boolean allowNull = false;
    private int minLength = 0;
    private int maxLength = 0;
    private String regularExpression;

    public StringValueValidator() {

    }

    public StringValueValidator(int minLength, int maxLength, String regularExpression, boolean allowNull) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.regularExpression = regularExpression;
        this.allowNull = allowNull;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return allowNull;
        }
        if (!(value instanceof String)) {
            return false;
        }
        String strValue = value.toString();
        if (minLength > 0 && strValue.length() < minLength) {
            return false;
        }
        if (maxLength > 0 && strValue.length() > maxLength) {
            return false;
        }
        if (regularExpression != null && !regularExpression.isEmpty()) {

            return strValue.matches(regularExpression);
        }
        return true;
    }
}
