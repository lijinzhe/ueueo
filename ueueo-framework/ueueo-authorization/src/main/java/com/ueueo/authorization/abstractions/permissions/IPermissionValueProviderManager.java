package com.ueueo.authorization.abstractions.permissions;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 16:38
 */
public interface IPermissionValueProviderManager {
    List<IPermissionValueProvider> valueProviders();
}
