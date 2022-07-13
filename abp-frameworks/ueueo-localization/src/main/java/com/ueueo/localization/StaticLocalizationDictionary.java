package com.ueueo.localization;

import lombok.Data;

import java.util.Map;

/**
 * Represents a simple implementation of <see cref="ILocalizationDictionary"/> interface.
 */
@Data
public class StaticLocalizationDictionary implements ILocalizationDictionary {

    private String cultureName;

    private Map<String, LocalizedString> dictionary;

    /**
     * Creates a new <see cref="StaticLocalizationDictionary"/> object.
     *
     * <param name="cultureName">Culture of the dictionary</param>
     * <param name="dictionary">The dictionary</param>
     */
    public StaticLocalizationDictionary(String cultureName, Map<String, LocalizedString> dictionary) {
        this.cultureName = cultureName;
        this.dictionary = dictionary;
    }

    @Override
    public LocalizedString getOrNull(String name) {
        return dictionary.get(name);
    }

    @Override
    public void fill(Map<String, LocalizedString> dictionary) {
        dictionary.putAll(this.dictionary);
    }

}
