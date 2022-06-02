package com.ueueo.localization.json;

import com.alibaba.fastjson.JSON;
import com.ueueo.AbpException;
import com.ueueo.localization.ILocalizationDictionary;
import com.ueueo.localization.LocalizedString;
import com.ueueo.localization.StaticLocalizationDictionary;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonLocalizationDictionaryBuilder {
    /**
     * Builds an <see cref="JsonLocalizationDictionaryBuilder" /> from given file.
     *
     * <param name="filePath">Path of the file</param>
     */
    public static ILocalizationDictionary buildFromFile(String filePath) {
        try {
            return buildFromJsonString(FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new AbpException("Invalid localization file format: " + filePath, ex);
        }
    }

    /**
     * Builds an <see cref="JsonLocalizationDictionaryBuilder" /> from given json string.
     *
     * <param name="jsonString">Json String</param>
     */
    public static ILocalizationDictionary buildFromJsonString(String jsonString) {
        JsonLocalizationFile jsonFile = null;
        try {
            jsonFile = JSON.parseObject(jsonString, JsonLocalizationFile.class);
        } catch (Exception e) {
            throw new AbpException("Can not parse json string. " + e.getMessage());
        }

        String cultureCode = jsonFile.getCulture();
        if (StringUtils.isBlank(cultureCode)) {
            throw new AbpException("Culture is empty in language json file.");
        }

        Map<String, LocalizedString> dictionary = new HashMap<>();
        List<String> dublicateNames = new ArrayList<>();
        for (Map.Entry<String, String> item : jsonFile.getTexts().entrySet()) {
            if (StringUtils.isBlank(item.getKey())) {
                throw new AbpException("The key is empty in given json string.");
            }

            if (dictionary.get(item.getKey()) != null) {
                dublicateNames.add(item.getKey());
            }
            dictionary.put(item.getKey(), new LocalizedString(item.getKey(), item.getValue()));
        }

        if (dublicateNames.size() > 0) {
            throw new AbpException(
                    "A dictionary can not contain same key twice. There are some duplicated names: " +
                            StringUtils.join(dublicateNames, ", "));
        }

        return new StaticLocalizationDictionary(cultureCode, dictionary);
    }
}
