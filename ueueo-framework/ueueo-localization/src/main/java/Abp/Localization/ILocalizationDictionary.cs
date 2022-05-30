using System.Collections.Generic;
using Microsoft.Extensions.Localization;

namespace Volo.Abp.Localization;

/**
 * Represents a dictionary that is used to find a localized string.
*/
public interface ILocalizationDictionary
{
    String CultureName;//  { get; }

    LocalizedString GetOrNull(String name);

    void Fill(Dictionary<String, LocalizedString> dictionary);
}
