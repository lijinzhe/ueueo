package com.ueueo.authorization.permissions;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 14:57
 */
public interface IPermissionChecker {
    Boolean isGranted(@NonNull String name);

    MultiplePermissionGrantResult isGranted(@NonNull List<String> names);

}
