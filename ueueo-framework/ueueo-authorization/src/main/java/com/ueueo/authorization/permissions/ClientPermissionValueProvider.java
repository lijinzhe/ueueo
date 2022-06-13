package com.ueueo.authorization.permissions;

import com.ueueo.multitenancy.ICurrentTenant;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class ClientPermissionValueProvider extends PermissionValueProvider {

    public final String ProviderName = "C";

    @Override
    public String getName() {
        return ProviderName;
    }

    protected ICurrentTenant currentTenant;

    public ClientPermissionValueProvider(IPermissionStore permissionStore, ICurrentTenant currentTenant) {
        super(permissionStore);
        this.currentTenant = currentTenant;
    }

    @Override
    public PermissionGrantResult check(PermissionValueCheckContext context) {
        String clientId = context.getPrincipal().findClientId();

        if (clientId == null) {
            return PermissionGrantResult.Undefined;
        }

        return permissionStore.isGranted(context.getPermission().getName(), getName(), clientId)
                ? PermissionGrantResult.Granted
                : PermissionGrantResult.Undefined;
    }

    @Override
    public MultiplePermissionGrantResult check(PermissionValuesCheckContext context) {
        List<String> permissionNames = context.getPermissions().stream().map(PermissionDefinition::getName).distinct().collect(Collectors.toList());
        Assert.isTrue(CollectionUtils.isNotEmpty(permissionNames), "PermissionNames must not empty!");

        String clientId = context.getPrincipal().findClientId();
        if (clientId == null) {
            return new MultiplePermissionGrantResult(permissionNames);
        }

        return permissionStore.isGranted(permissionNames, getName(), clientId);
    }

    public ICurrentTenant getCurrentTenant() {
        return currentTenant;
    }
}
