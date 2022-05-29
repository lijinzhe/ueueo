package com.ueueo.localization;

import java.util.List;

/**
 * C# Microsoft.Extensions.Localization
 *
 * Represents a service that provides localized strings.
 *
 * @author Lee
 * @date 2022-05-17 14:41
 */
public interface IStringLocalizer {

    /**
     * Gets the string resource with the given name.
     *
     * @param name The name of the string resource.
     *
     * @return The string resource as a Microsoft.Extensions.Localization.LocalizedString.
     */
    LocalizedString get(String name);

    /**
     * Gets the string resource with the given name and formatted with the supplied  arguments.
     *
     * @param name      The name of the string resource.
     * @param arguments The values to format the string with.
     *
     * @return The formatted string resource as a Microsoft.Extensions.Localization.LocalizedString.
     */
    LocalizedString get(String name, Object... arguments);

    /**
     * Gets all string resources.
     *
     * @param includeParentCultures A System.Boolean indicating whether to include strings from parent cultures.
     *
     * @return The strings.
     */
    List<LocalizedString> getAllStrings(boolean includeParentCultures);
}
