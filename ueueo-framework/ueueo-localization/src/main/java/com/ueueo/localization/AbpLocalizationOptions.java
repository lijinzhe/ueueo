package com.ueueo.localization;

import com.ueueo.NameValue;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AbpLocalizationOptions {
    private LocalizationResourceDictionary resources;

    /**
     * Used as the default resource when resource was not specified on a localization operation.
     */
    @Setter
    private Class<?> defaultResourceType;

    private List<Class<? extends ILocalizationResourceContributor>> globalContributors;

    private List<LanguageInfo> languages;

    private Map<String, List<NameValue<String>>> languagesMap;

    private Map<String, List<NameValue<String>>> languageFilesMap;
    @Setter
    private boolean tryToGetFromBaseCulture;
    @Setter
    private boolean tryToGetFromDefaultCulture;

    public AbpLocalizationOptions() {
        resources = new LocalizationResourceDictionary();
        globalContributors = new ArrayList<>();
        languages = new ArrayList<>();
        languagesMap = new HashMap<>();
        languageFilesMap = new HashMap<>();
        tryToGetFromBaseCulture = true;
        tryToGetFromDefaultCulture = true;
    }

    public AbpLocalizationOptions addLanguagesMapOrUpdate(String packageName, List<NameValue<String>> maps) {
        for (NameValue<String> map : maps) {
            addOrUpdate(languagesMap, packageName, map);
        }

        return this;
    }

    public String getLanguagesMap(String packageName, String language) {
        List<NameValue<String>> maps = languagesMap.get(packageName);
        if (maps != null) {
            NameValue<String> map = maps.stream().filter(x -> x.getName().equals(language))
                    .findFirst().orElse(null);
            if (map != null && map.getValue() != null) {
                return map.getValue();
            }
        }
        return language;
    }
    //TODO 获取当前文化语言
    //    public  String GetCurrentUICultureLanguagesMap(  String packageName)
    //    {
    //        return GetLanguagesMap(this, packageName, CultureInfo.CurrentUICulture.Name);
    //    }

    public AbpLocalizationOptions addLanguageFilesMapOrUpdate(
            String packageName, List<NameValue<String>> maps) {
        for (NameValue<String> map : maps) {
            addOrUpdate(languageFilesMap, packageName, map);
        }

        return this;
    }

    public String getLanguageFilesMap(String packageName,
                                      String language) {
        List<NameValue<String>> maps = languageFilesMap.get(packageName);
        if (maps != null) {
            NameValue<String> map = maps.stream().filter(x -> x.getName().equals(language))
                    .findFirst().orElse(null);
            if (map != null && map.getValue() != null) {
                return map.getValue();
            }
        }
        return language;
    }
    //TODO 获取当前文化语言
    //    public  String GetCurrentUICultureLanguageFilesMap(  String packageName)
    //    {
    //        return GetLanguageFilesMap(this, packageName, CultureInfo.CurrentUICulture.Name);
    //    }

    private void addOrUpdate(Map<String, List<NameValue<String>>> maps, String packageName, NameValue<String> value) {
        List<NameValue<String>> existMaps = maps.get(packageName);
        if (existMaps != null) {
            existMaps.forEach(x -> {
                if (x.getName().equals(value.getName())) {
                    x.setValue(value.getValue());
                }
            });
        } else {
            maps.put(packageName, new ArrayList<NameValue<String>>() {{add(value);}});
        }
    }
}
