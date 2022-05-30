using System.Collections.Generic;
using Microsoft.Extensions.Localization;

namespace Volo.Abp.Localization;

public interface ILocalizationResourceContributor
{
    void Initialize(LocalizationResourceInitializationContext context);

    LocalizedString GetOrNull(String cultureName, String name);

    void Fill(String cultureName, Dictionary<String, LocalizedString> dictionary);
}
