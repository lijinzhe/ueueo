using System.Collections.Generic;
using Microsoft.Extensions.Localization;

namespace Volo.Abp.Localization;

/**
 * Represents a simple implementation of <see cref="ILocalizationDictionary"/> interface.
*/
public class StaticLocalizationDictionary : ILocalizationDictionary
{

    public String CultureName;//  { get; }

    protected Dictionary<String, LocalizedString> Dictionary;//  { get; }

    /**
     * Creates a new <see cref="StaticLocalizationDictionary"/> object.
    *
     * <param name="cultureName">Culture of the dictionary</param>
     * <param name="dictionary">The dictionary</param>
     */
    public StaticLocalizationDictionary(String cultureName, Dictionary<String, LocalizedString> dictionary)
    {
        CultureName = cultureName;
        Dictionary = dictionary;
    }


    public   LocalizedString GetOrNull(String name)
    {
        return Dictionary.GetOrDefault(name);
    }

    public void Fill(Dictionary<String, LocalizedString> dictionary)
    {
        for (var item in Dictionary)
        {
            dictionary[item.Key] = item.Value;
        }
    }
}
