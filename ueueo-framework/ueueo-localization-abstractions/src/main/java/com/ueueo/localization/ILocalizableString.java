package com.ueueo.localization;

/**
 * @author Lee
 * @date 2022-05-17 14:36
 */
public interface ILocalizableString {
    LocalizedString localize(IStringLocalizerFactory stringLocalizerFactory);
}
