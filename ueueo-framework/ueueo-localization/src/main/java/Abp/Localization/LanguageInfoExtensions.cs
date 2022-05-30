using System.Collections.Generic;
using System.Linq;
using JetBrains.Annotations;

namespace Volo.Abp.Localization;

public static class LanguageInfoExtensions
{
    public static T FindByCulture<T>(
        @Nonnull this IEnumerable<T> languages,
        @Nonnull String cultureName,
        @Nullable String uiCultureName = null)
    where T : class, ILanguageInfo
    {
        if (uiCultureName == null)
        {
            uiCultureName = cultureName;
        }

        var languageList = languages.ToList();

        return languageList.FirstOrDefault(l => l.CultureName == cultureName && l.UiCultureName == uiCultureName)
               ?? languageList.FirstOrDefault(l => l.CultureName == cultureName)
               ?? languageList.FirstOrDefault(l => l.UiCultureName == uiCultureName);
    }
}
