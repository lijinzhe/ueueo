package com.ueueo.localization.json;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class JsonLocalizationFile {
    /**
     * Culture name; eg : en , en-us, zh-CN
     */
    private String culture;

    private Map<String, String> texts;

    public JsonLocalizationFile() {
        texts = new HashMap<>();
    }
}
