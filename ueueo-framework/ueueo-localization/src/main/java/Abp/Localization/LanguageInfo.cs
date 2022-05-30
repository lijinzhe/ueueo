using System;
using JetBrains.Annotations;

namespace Volo.Abp.Localization;

[Serializable]
public class LanguageInfo : ILanguageInfo
{
    [NotNull]
    public   String CultureName { get; protected set; }

    [NotNull]
    public   String UiCultureName { get; protected set; }

    [NotNull]
    public   String DisplayName { get; protected set; }

    [CanBeNull]
    public   String FlagIcon;// { get; set; }

    protected LanguageInfo()
    {

    }

    public LanguageInfo(
        String cultureName,
        String uiCultureName = null,
        String displayName = null,
        String flagIcon = null)
    {
        ChangeCultureInternal(cultureName, uiCultureName, displayName);
        FlagIcon = flagIcon;
    }

    public   void ChangeCulture(String cultureName, String uiCultureName = null, String displayName = null)
    {
        ChangeCultureInternal(cultureName, uiCultureName, displayName);
    }

    private void ChangeCultureInternal(String cultureName, String uiCultureName, String displayName)
    {
        CultureName = Check.NotNullOrWhiteSpace(cultureName, nameof(cultureName));

        UiCultureName = !uiCultureName.IsNullOrWhiteSpace()
            ? uiCultureName
            : cultureName;

        DisplayName = !displayName.IsNullOrWhiteSpace()
            ? displayName
            : cultureName;
    }
}
