package com.ueueo.text.formatting;

import lombok.Data;

/**
 *
 * @author Lee
 * @date 2022-05-29 13:21
 */
@Data
class FormatStringToken {
    private String text;

    private FormatStringTokenTypeEnum type;

    public FormatStringToken(String text, FormatStringTokenTypeEnum type) {
        this.text = text;
        this.type = type;
    }
}
