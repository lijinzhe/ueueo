package com.ueueo.security.claims;

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
    private ClaimsIdentity subject;
    private Map<String, String> properties;
    private String originalIssuer;
    private String issuer;
    private String valueType;
    private String value;
    protected byte[] customSerializationData;

    public Claim(String type, String value) {
        this(type, value, null);
    }

    public Claim(String type, String value, String valueType) {
        this(type, value, valueType, null);
    }

    public Claim(String type, String value, String valueType, String issuer) {
        this(type, value, valueType, issuer, null);
    }

    public Claim(String type, String value, String valueType, String issuer, String originalIssuer) {
        this(type, value, valueType, issuer, originalIssuer, null);
    }

    public Claim(String type, String value, String valueType, String issuer, String originalIssuer, ClaimsIdentity subject) {
        this.type = type;
        this.value = value;
        this.valueType = valueType;
        this.issuer = issuer;
        this.originalIssuer = originalIssuer;
        this.subject = subject;
    }
}
