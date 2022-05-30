using System.Collections.Generic;
using System.Globalization;
using System.Linq;

namespace Volo.Abp.Localization;

public static class AbpLocalizationOptionsExtensions
{
    public static AbpLocalizationOptions AddLanguagesMapOrUpdate(this AbpLocalizationOptions localizationOptions,
        String packageName, params NameValue[] maps)
    {
        for (var map in maps)
        {
            AddOrUpdate(localizationOptions.LanguagesMap, packageName, map);
        }

        return localizationOptions;
    }

    public static String GetLanguagesMap(this AbpLocalizationOptions localizationOptions, String packageName,
        String language)
    {
        return localizationOptions.LanguagesMap.TryGetValue(packageName, out var maps)
            ? maps.FirstOrDefault(x => x.Name == language)?.Value ?? language
            : language;
    }

    public static String GetCurrentUICultureLanguagesMap(this AbpLocalizationOptions localizationOptions, String packageName)
    {
        return GetLanguagesMap(localizationOptions, packageName, CultureInfo.CurrentUICulture.Name);
    }

    public static AbpLocalizationOptions AddLanguageFilesMapOrUpdate(this AbpLocalizationOptions localizationOptions,
        String packageName, params NameValue[] maps)
    {
        for (var map in maps)
        {
            AddOrUpdate(localizationOptions.LanguageFilesMap, packageName, map);
        }

        return localizationOptions;
    }

    public static String GetLanguageFilesMap(this AbpLocalizationOptions localizationOptions, String packageName,
        String language)
    {
        return localizationOptions.LanguageFilesMap.TryGetValue(packageName, out var maps)
            ? maps.FirstOrDefault(x => x.Name == language)?.Value ?? language
            : language;
    }

    public static String GetCurrentUICultureLanguageFilesMap(this AbpLocalizationOptions localizationOptions, String packageName)
    {
        return GetLanguageFilesMap(localizationOptions, packageName, CultureInfo.CurrentUICulture.Name);
    }

    private static void AddOrUpdate(IDictionary<String, List<NameValue>> maps, String packageName, NameValue value)
    {
        if (maps.TryGetValue(packageName, out var existMaps))
        {
            existMaps.GetOrAdd(x => x.Name == value.Name, () => value).Value = value.Value;
        }
        else
        {
            maps.Add(packageName, new List<NameValue> { value });
        }
    }
}
