package com.ueueo.validation.stringvalues;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lee
 * @date 2022-05-29 16:52
 */
@EqualsAndHashCode(callSuper = true)
@StringValueTypeAttribute(name = "SELECTION")
@Data
public class SelectionStringValueType extends StringValueTypeBase {

    private ISelectionStringValueItemSource itemSource;

    public SelectionStringValueType() {
        super();
    }

    public SelectionStringValueType(IValueValidator validator) {
        super(validator);
    }
}
