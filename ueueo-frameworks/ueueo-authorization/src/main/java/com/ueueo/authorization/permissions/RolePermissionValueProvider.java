package com.ueueo.authorization.permissions;

import com.ueueo.security.claims.Claim;
import com.ueueo.security.claims.ClaimTypes;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RolePermissionValueProvider extends PermissionValueProvider {
    public final String ProviderName = "R";

    @Override
    public String getName() {
        return ProviderName;
    }

    public RolePermissionValueProvider(IPermissionStore permissionStore) {
        super(permissionStore);

    }

    @Override
    public PermissionGrantResult check(PermissionValueCheckContext context) {
        List<String> roles = context.getPrincipal().findAll(ClaimTypes.Role).stream()
                .map(Claim::getValue).distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(roles)) {
            return PermissionGrantResult.Undefined;
        }

        for (String role : roles) {
            if (permissionStore.isGranted(context.getPermission().getName(), getName(), role)) {
                return PermissionGrantResult.Granted;
            }
        }

        return PermissionGrantResult.Undefined;
    }

    @Override
    public MultiplePermissionGrantResult check(PermissionValuesCheckContext context) {
        List<String> permissionNames = context.getPermissions().stream().map(PermissionDefinition::getName).distinct().collect(Collectors.toList());
        Assert.isTrue(CollectionUtils.isNotEmpty(permissionNames), "PermissionNames must not empty!");

        MultiplePermissionGrantResult result = new MultiplePermissionGrantResult(permissionNames);

        List<String> roles = context.getPrincipal().findAll(ClaimTypes.Role).stream()
                .map(Claim::getValue).distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roles)) {
            return result;
        }

        for (String role : roles) {
            MultiplePermissionGrantResult multipleResult = permissionStore.isGranted(permissionNames, getName(), role);
            List<Map.Entry<String, PermissionGrantResult>> grantResults = multipleResult.getResult().entrySet().stream().filter(entry ->
                    result.getResult().containsKey(entry.getKey())
                            && result.getResult().get(entry.getKey()).equals(PermissionGrantResult.Undefined)
                            && !entry.getValue().equals(PermissionGrantResult.Undefined)
            ).collect(Collectors.toList());
            for (Map.Entry<String, PermissionGrantResult> grantResult : grantResults) {
                result.getResult().put(grantResult.getKey(), grantResult.getValue());
                permissionNames.removeIf(s -> s.equals(grantResult.getKey()));
            }

            if (result.allGranted() || result.allProhibited()) {
                break;
            }

            if (CollectionUtils.isEmpty(permissionNames)) {
                break;
            }
        }

        return result;
    }
}
