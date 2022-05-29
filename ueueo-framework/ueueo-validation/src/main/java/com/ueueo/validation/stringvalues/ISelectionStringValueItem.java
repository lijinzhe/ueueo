package com.ueueo.validation.stringvalues;

/**
 * @author Lee
 * @date 2022-05-29 16:36
 */
public interface ISelectionStringValueItem {
    String getValue();

    void setValue(String value);

    LocalizableStringInfo getDisplayText();

    void setDisplayText(LocalizableStringInfo displayText);
}
