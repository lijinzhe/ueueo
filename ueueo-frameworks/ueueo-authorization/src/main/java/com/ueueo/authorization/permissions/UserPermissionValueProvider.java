package com.ueueo.authorization.permissions;

import com.ueueo.ID;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class UserPermissionValueProvider extends PermissionValueProvider {
    public final String ProviderName = "U";

    @Override
    public String getName() {
        return ProviderName;
    }

    public UserPermissionValueProvider(IPermissionStore permissionStore) {
        super(permissionStore);

    }

    @Override
    public PermissionGrantResult check(PermissionValueCheckContext context) {
        ID userId = context.getPrincipal().findUserId();

        if (userId == null) {
            return PermissionGrantResult.Undefined;
        }

        return permissionStore.isGranted(context.getPermission().getName(), getName(), userId.toString())
                ? PermissionGrantResult.Granted
                : PermissionGrantResult.Undefined;
    }

    @Override
    public MultiplePermissionGrantResult check(PermissionValuesCheckContext context) {
        List<String> permissionNames = context.getPermissions().stream().map(PermissionDefinition::getName).distinct().collect(Collectors.toList());
        Assert.isTrue(CollectionUtils.isNotEmpty(permissionNames), "PermissionNames must not empty!");

        ID userId = context.getPrincipal().findUserId();
        if (userId == null) {
            return new MultiplePermissionGrantResult(permissionNames);
        }

        return permissionStore.isGranted(permissionNames, getName(), userId.toString());
    }
}
