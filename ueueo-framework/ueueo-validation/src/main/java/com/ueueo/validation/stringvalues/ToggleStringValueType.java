package com.ueueo.validation.stringvalues;

/**
 * @author Lee
 * @date 2022-05-29 16:55
 */
@StringValueTypeAttribute(name = "TOGGLE")
public class ToggleStringValueType extends StringValueTypeBase {

    public ToggleStringValueType() {
        this(new BooleanValueValidator());

    }

    public ToggleStringValueType(IValueValidator validator) {
        super(validator);
    }
}
