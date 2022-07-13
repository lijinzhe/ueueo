package com.ueueo.authorization.permissions;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-05 20:41
 */
public interface IPermissionDefinitionProviderManager {
    List<IPermissionDefinitionProvider> getDefinitionProvider();
}
