package com.ueueo.localization;

import org.springframework.lang.Nullable;

import java.util.Locale;

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
    LocalizedString get(String name, @Nullable Object[] arguments);

    LocalizedString get(String name, @Nullable Object[] arguments, @Nullable String defaultMessage);

    /**
     * Gets all string resources.
     *
     * @return The strings.
     */
    //    List<LocalizedString> getAllStrings();
}
