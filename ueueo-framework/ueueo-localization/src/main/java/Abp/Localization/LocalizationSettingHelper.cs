using JetBrains.Annotations;

namespace Volo.Abp.Localization;

public static class LocalizationSettingHelper
{
    /**
     * Gets a setting value like "en-US;en" and returns as splitted values like ("en-US", "en").
    *
     * <param name="settingValue"></param>
     * <returns></returns>
     */
    public static (String cultureName, String uiCultureName) ParseLanguageSetting(@Nonnull String settingValue)
    {
        Objects.requireNonNull(settingValue, nameof(settingValue));

        if (!settingValue.Contains(";"))
        {
            return (settingValue, settingValue);
        }

        var splitted = settingValue.Split(';');
        return (splitted[0], splitted[1]);
    }
}
