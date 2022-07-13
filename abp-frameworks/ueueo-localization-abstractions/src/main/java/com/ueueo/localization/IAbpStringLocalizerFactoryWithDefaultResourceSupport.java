package com.ueueo.localization;

import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-17 15:03
 */
public interface IAbpStringLocalizerFactoryWithDefaultResourceSupport {
    @Nullable
    IStringLocalizer createDefaultOrNull();
}
