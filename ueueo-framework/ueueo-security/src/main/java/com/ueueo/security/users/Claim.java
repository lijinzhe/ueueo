package com.ueueo.security.users;

import lombok.Getter;

import java.util.Map;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-14 11:01
 */
@Getter
public class Claim {

    private String type;
    private String value;
    private String valueType;
    private String issuer;
    private Map<String, String> properties;

    public Claim(String type, String value) {
        this(type, value, null);
    }

    public Claim(String type, String value, String valueType) {
        this(type, value, valueType, null);
    }

    public Claim(String type, String value, String valueType, String issuer) {
        this.type = type;
        this.value = value;
        this.valueType = valueType;
        this.issuer = issuer;
    }
}
