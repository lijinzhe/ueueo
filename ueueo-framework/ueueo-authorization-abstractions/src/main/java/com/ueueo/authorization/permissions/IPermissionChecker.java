package com.ueueo.authorization.permissions;

import com.ueueo.principal.ClaimsPrincipal;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 14:57
 */
public interface IPermissionChecker {
    Boolean isGranted(@NonNull String name);

    Boolean isGranted(@NonNull ClaimsPrincipal claimsPrincipal, @NonNull String name);

    MultiplePermissionGrantResult isGranted(@NonNull List<String> names);

    MultiplePermissionGrantResult isGranted(@NonNull ClaimsPrincipal claimsPrincipal, @NonNull List<String> names);

}
