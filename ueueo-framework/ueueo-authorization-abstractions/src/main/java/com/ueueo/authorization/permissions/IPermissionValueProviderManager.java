package com.ueueo.authorization.permissions;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 16:38
 */
public interface IPermissionValueProviderManager {

    List<IPermissionValueProvider> getValueProviders();
}
