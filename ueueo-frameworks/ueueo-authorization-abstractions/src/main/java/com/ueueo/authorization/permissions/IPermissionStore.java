package com.ueueo.authorization.permissions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 16:28
 */
public interface IPermissionStore {

    Boolean isGranted(@NonNull String name, @Nullable String providerName, @Nullable String providerKey);

    MultiplePermissionGrantResult isGranted(@NonNull List<String> names, @Nullable String providerName, @Nullable String providerKey);
}
