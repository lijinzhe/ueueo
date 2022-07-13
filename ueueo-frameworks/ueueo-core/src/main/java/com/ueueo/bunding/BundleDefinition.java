package com.ueueo.bunding;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lee
 * @date 2022-05-29 11:30
 */
@Data
public class BundleDefinition {
    private String source;

    private Map<String, String> additionalProperties;

    private boolean excludeFromBundle;

    public BundleDefinition() {
        additionalProperties = new HashMap<>();
    }
}
