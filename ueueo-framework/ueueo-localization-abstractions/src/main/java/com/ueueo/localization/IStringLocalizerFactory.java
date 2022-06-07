package com.ueueo.localization;

/**
 * C# Microsoft.Extensions.Localization
 *
 * Represents a factory that creates Microsoft.Extensions.Localization.IStringLocalizer instances.
 *
 * @author Lee
 * @date 2022-05-17 14:39
 */
public interface IStringLocalizerFactory {

    /**
     * Creates an Microsoft.Extensions.Localization.IStringLocalizer using the System.Reflection.Assembly
     * and System.Type.FullName of the specified System.Type.
     *
     * @param resourceSource The System.Type.
     *
     * @return The Microsoft.Extensions.Localization.IStringLocalizer.
     */
    IStringLocalizer create(Class<?> resourceSource);

    /**
     * Creates an Microsoft.Extensions.Localization.IStringLocalizer.
     *
     * @param basenames The base name of the resource to load strings from.
     *
     * @return The Microsoft.Extensions.Localization.IStringLocalizer.
     */
    IStringLocalizer create(String... basenames);

}
