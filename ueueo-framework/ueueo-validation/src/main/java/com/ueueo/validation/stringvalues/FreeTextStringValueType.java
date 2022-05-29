package com.ueueo.validation.stringvalues;

/**
 *
 * @author Lee
 * @date 2022-05-29 16:49
 */
@StringValueTypeAttribute(name = "FREE_TEXT")
public class FreeTextStringValueType extends StringValueTypeBase {

    public FreeTextStringValueType(IValueValidator validator) {
        super(validator);
    }

    public FreeTextStringValueType() {
        super();
    }
}
