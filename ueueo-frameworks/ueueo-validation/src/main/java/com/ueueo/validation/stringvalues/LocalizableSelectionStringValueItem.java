package com.ueueo.validation.stringvalues;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-29 16:51
 */
@Data
public class LocalizableSelectionStringValueItem implements ISelectionStringValueItem {
    private String value;

    private LocalizableStringInfo displayText;

}
