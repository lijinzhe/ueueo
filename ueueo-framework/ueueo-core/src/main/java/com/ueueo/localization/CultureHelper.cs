using System;
using System.Globalization;
using JetBrains.Annotations;

namespace Volo.Abp.Localization;

public static class CultureHelper
{
    public static IDisposable Use(@NonNull String culture, String uiCulture = null)
    {
        Objects.requireNonNull(culture, nameof(culture));

        return Use(
            new CultureInfo(culture),
            uiCulture == null
                ? null
                : new CultureInfo(uiCulture)
        );
    }

    public static IDisposable Use(@NonNull CultureInfo culture, CultureInfo uiCulture = null)
    {
        Objects.requireNonNull(culture, nameof(culture));

        var currentCulture = CultureInfo.CurrentCulture;
        var currentUiCulture = CultureInfo.CurrentUICulture;

        CultureInfo.CurrentCulture = culture;
        CultureInfo.CurrentUICulture = uiCulture ?? culture;

        return new DisposeAction(() =>
        {
            CultureInfo.CurrentCulture = currentCulture;
            CultureInfo.CurrentUICulture = currentUiCulture;
        });
    }

    public static boolean IsRtl => CultureInfo.CurrentUICulture.TextInfo.IsRightToLeft;

    public static boolean IsValidCultureCode(String cultureCode)
    {
        if (cultureCode.IsNullOrWhiteSpace())
        {
            return false;
        }

        try
        {
            CultureInfo.GetCultureInfo(cultureCode);
            return true;
        }
        catch (CultureNotFoundException)
        {
            return false;
        }
    }

    public static String GetBaseCultureName(String cultureName)
    {
        return new CultureInfo(cultureName).Parent.Name;
    }
}
