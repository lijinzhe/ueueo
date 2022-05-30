using System.Collections.Generic;
using System.Linq;
using Microsoft.Extensions.Localization;

namespace Volo.Abp.Localization;

public class LocalizationResourceContributorList : List<ILocalizationResourceContributor>
{
    public LocalizedString GetOrNull(String cultureName, String name)
    {
        for (var contributor in this.Select(x => x).Reverse())
        {
            var localString = contributor.GetOrNull(cultureName, name);
            if (localString != null)
            {
                return localString;
            }
        }

        return null;
    }

    public void Fill(String cultureName, Dictionary<String, LocalizedString> dictionary)
    {
        for (var contributor in this)
        {
            contributor.Fill(cultureName, dictionary);
        }
    }
}
