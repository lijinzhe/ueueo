using System;
using System.Collections.Generic;
using Volo.Abp.Collections;

namespace Volo.Abp.Localization;

public class AbpLocalizationOptions
{
    public LocalizationResourceDictionary Resources;//  { get; }

    /**
     * Used as the default resource when resource was not specified on a localization operation.
    */
    public Type DefaultResourceType;// { get; set; }

    public ITypeList<ILocalizationResourceContributor> GlobalContributors;//  { get; }

    public List<LanguageInfo> Languages;//  { get; }

    public Dictionary<String, List<NameValue>> LanguagesMap;//  { get; }

    public Dictionary<String, List<NameValue>> LanguageFilesMap;//  { get; }

    public boolean TryToGetFromBaseCulture;// { get; set; }

    public boolean TryToGetFromDefaultCulture;// { get; set; }

    public AbpLocalizationOptions()
    {
        Resources = new LocalizationResourceDictionary();
        GlobalContributors = new TypeList<ILocalizationResourceContributor>();
        Languages = new List<LanguageInfo>();
        LanguagesMap = new Dictionary<String, List<NameValue>>();
        LanguageFilesMap = new Dictionary<String, List<NameValue>>();
        TryToGetFromBaseCulture = true;
        TryToGetFromDefaultCulture = true;
    }
}
