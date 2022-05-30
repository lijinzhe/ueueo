using System.Collections.Generic;
using System.Reflection;
using JetBrains.Annotations;
using Microsoft.Extensions.Localization;
using Volo.Abp.DynamicProxy;
using Volo.Abp.Reflection;

namespace Volo.Abp.Localization;

public static class AbpStringLocalizerExtensions
{
    [NotNull]
    public static IStringLocalizer GetInternalLocalizer(
        @Nonnull this IStringLocalizer StringLocalizer)
    {
        Objects.requireNonNull(StringLocalizer, nameof(StringLocalizer));

        var localizerType = StringLocalizer.GetType();
        if (!ReflectionHelper.IsAssignableToGenericType(localizerType, typeof(StringLocalizer<>)))
        {
            return StringLocalizer;
        }

        var localizerField = localizerType
            .GetField(
                "_localizer",
                BindingFlags.Instance |
                BindingFlags.NonPublic
            );

        if (localizerField == null)
        {
            throw new AbpException($"Could not find the _localizer field inside the {typeof(StringLocalizer<>).FullName} class. Probably its name has changed. Please report this issue to the ABP framework.");
        }

        return localizerField.GetValue(StringLocalizer) as IStringLocalizer;
    }

    public static IEnumerable<LocalizedString> GetAllStrings(
        this IStringLocalizer StringLocalizer,
        boolean includeParentCultures,
        boolean includeBaseLocalizers)
    {
        var internalLocalizer = (ProxyHelper.UnProxy(StringLocalizer) as IStringLocalizer).GetInternalLocalizer();
        if (internalLocalizer is IStringLocalizerSupportsInheritance StringLocalizerSupportsInheritance)
        {
            return StringLocalizerSupportsInheritance.GetAllStrings(
                includeParentCultures,
                includeBaseLocalizers
            );
        }

        return StringLocalizer.GetAllStrings(
            includeParentCultures
        );
    }
}
