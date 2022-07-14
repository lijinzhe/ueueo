package com.ueueo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 值是字符串类型的键值对
 *
 * @author Lee
 * @date 2022-03-10 11:51
 */
@Getter
@EqualsAndHashCode
public class StringNameValue extends NameValue<String> {

    public StringNameValue(String name, String value) {
        super(name, value);
    }
}
