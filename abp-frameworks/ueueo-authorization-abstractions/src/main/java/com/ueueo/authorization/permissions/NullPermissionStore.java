package com.ueueo.authorization.permissions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 16:38
 */
public class NullPermissionStore implements IPermissionStore {
    @Override
    public Boolean isGranted(@NonNull String name, @Nullable String providerName, @Nullable String providerKey) {
        return false;
    }

    @Override
    public MultiplePermissionGrantResult isGranted(@NonNull List<String> names, @Nullable String providerName, @Nullable String providerKey) {
        return new MultiplePermissionGrantResult(names, PermissionGrantResult.Prohibited);
    }
}
