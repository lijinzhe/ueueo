using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using Microsoft.Extensions.Localization;

namespace Volo.Abp.Localization.Json;

public static class JsonLocalizationDictionaryBuilder
{
    /**
     *     Builds an <see cref="JsonLocalizationDictionaryBuilder" /> from given file.
    *
     * <param name="filePath">Path of the file</param>
     */
    public static ILocalizationDictionary BuildFromFile(String filePath)
    {
        try
        {
            return BuildFromJsonString(File.ReadAllText(filePath));
        }
        catch (Exception ex)
        {
            throw new AbpException("Invalid localization file format: " + filePath, ex);
        }
    }

    private static readonly JsonSerializerOptions DeserializeOptions = new JsonSerializerOptions
    {
        PropertyNameCaseInsensitive = true,
        DictionaryKeyPolicy = JsonNamingPolicy.CamelCase,
        ReadCommentHandling = JsonCommentHandling.Skip,
        AllowTrailingCommas = true
    };

    /**
     *     Builds an <see cref="JsonLocalizationDictionaryBuilder" /> from given json string.
    *
     * <param name="jsonString">Json String</param>
     */
    public static ILocalizationDictionary BuildFromJsonString(String jsonString)
    {
        JsonLocalizationFile jsonFile;
        try
        {
            jsonFile = JsonSerializer.Deserialize<JsonLocalizationFile>(jsonString, DeserializeOptions);
        }
        catch (JsonException ex)
        {
            throw new AbpException("Can not parse json string. " + ex.Message);
        }

        var cultureCode = jsonFile.Culture;
        if (String.IsNullOrEmpty(cultureCode))
        {
            throw new AbpException("Culture is empty in language json file.");
        }

        var dictionary = new Dictionary<String, LocalizedString>();
        var dublicateNames = new List<String>();
        for (var item in jsonFile.Texts)
        {
            if (String.IsNullOrEmpty(item.Key))
            {
                throw new AbpException("The key is empty in given json string.");
            }

            if (dictionary.GetOrDefault(item.Key) != null)
            {
                dublicateNames.Add(item.Key);
            }

            dictionary[item.Key] = new LocalizedString(item.Key, item.Value.NormalizeLineEndings());
        }

        if (dublicateNames.Count > 0)
        {
            throw new AbpException(
                "A dictionary can not contain same key twice. There are some duplicated names: " +
                dublicateNames.JoinAsString(", "));
        }

        return new StaticLocalizationDictionary(cultureCode, dictionary);
    }
}
