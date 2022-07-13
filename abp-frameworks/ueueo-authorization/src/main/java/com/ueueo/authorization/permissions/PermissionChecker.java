package com.ueueo.authorization.permissions;

import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.multitenancy.MultiTenancySides;
import com.ueueo.security.principal.ClaimsPrincipal;
import com.ueueo.security.claims.ICurrentPrincipalAccessor;
import com.ueueo.simplestatechecking.ISimpleStateCheckerManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PermissionChecker implements IPermissionChecker {

    protected IPermissionDefinitionManager permissionDefinitionManager;
    protected ICurrentPrincipalAccessor principalAccessor;
    protected ICurrentTenant currentTenant;
    protected IPermissionValueProviderManager permissionValueProviderManager;
    protected ISimpleStateCheckerManager<PermissionDefinition> stateCheckerManager;

    public PermissionChecker(
            ICurrentPrincipalAccessor principalAccessor,
            IPermissionDefinitionManager permissionDefinitionManager,
            ICurrentTenant currentTenant,
            IPermissionValueProviderManager permissionValueProviderManager,
            ISimpleStateCheckerManager<PermissionDefinition> stateCheckerManager) {
        this.principalAccessor = principalAccessor;
        this.permissionDefinitionManager = permissionDefinitionManager;
        this.currentTenant = currentTenant;
        this.permissionValueProviderManager = permissionValueProviderManager;
        this.stateCheckerManager = stateCheckerManager;
    }

    @Override
    public Boolean isGranted(@NonNull String name) {
        return isGranted(principalAccessor.getCurrentPrincipal(), name);
    }

    @Override
    public Boolean isGranted(ClaimsPrincipal claimsPrincipal, @NonNull String name) {
        Objects.requireNonNull(name);

        PermissionDefinition permission = permissionDefinitionManager.get(name);

        if (!permission.getIsEnabled()) {
            return false;
        }

        if (!stateCheckerManager.isEnabled(permission)) {
            return false;
        }

        MultiTenancySides multiTenancySide = claimsPrincipal != null ? MultiTenancySides.getMultiTenancySide(claimsPrincipal)
                : currentTenant.getMultiTenancySide();

        if (!permission.getMultiTenancySide().hasFlag(multiTenancySide)) {
            return false;
        }

        boolean isGranted = false;
        PermissionValueCheckContext context = new PermissionValueCheckContext(permission, claimsPrincipal);
        for (IPermissionValueProvider provider : permissionValueProviderManager.getValueProviders()) {
            if (CollectionUtils.isNotEmpty(context.getPermission().getAllowedProviders()) &&
                    !context.getPermission().getAllowedProviders().contains(provider.getName())) {
                continue;
            }

            PermissionGrantResult result = provider.check(context);

            if (result == PermissionGrantResult.Granted) {
                isGranted = true;
            } else if (result == PermissionGrantResult.Prohibited) {
                return false;
            }
        }

        return isGranted;
    }

    @Override
    public MultiplePermissionGrantResult isGranted(List<String> names) {
        return isGranted(principalAccessor.getCurrentPrincipal(), names);
    }

    @Override
    public MultiplePermissionGrantResult isGranted(ClaimsPrincipal claimsPrincipal, List<String> names) {
        Objects.requireNonNull(names);

        MultiTenancySides multiTenancySide = claimsPrincipal != null ? MultiTenancySides.getMultiTenancySide(claimsPrincipal)
                : currentTenant.getMultiTenancySide();

        MultiplePermissionGrantResult result = new MultiplePermissionGrantResult();
        if (CollectionUtils.isEmpty(names)) {
            return result;
        }

        List<PermissionDefinition> permissionDefinitions = new ArrayList<>();
        for (String name : names) {
            PermissionDefinition permission = permissionDefinitionManager.get(name);

            result.getResult().put(name, PermissionGrantResult.Undefined);

            if (permission.getIsEnabled() &&
                    stateCheckerManager.isEnabled(permission) &&
                    permission.getMultiTenancySide().hasFlag(multiTenancySide)) {
                permissionDefinitions.add(permission);
            }
        }

        for (IPermissionValueProvider provider : permissionValueProviderManager.getValueProviders()) {
            List<PermissionDefinition> permissions = permissionDefinitions
                    .stream().filter(x -> CollectionUtils.isEmpty(x.getAllowedProviders()) || x.getAllowedProviders().contains(provider.getName()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(permissions)) {
                break;
            }

            PermissionValuesCheckContext context = new PermissionValuesCheckContext(
                    permissions,
                    claimsPrincipal);

            MultiplePermissionGrantResult multipleResult = provider.check(context);

            for (Map.Entry<String, PermissionGrantResult> grantResult : multipleResult.getResult().entrySet().stream()
                    .filter(grantResult ->
                            result.getResult().containsKey(grantResult.getKey()) &&
                                    result.getResult().get(grantResult.getKey()) == PermissionGrantResult.Undefined &&
                                    grantResult.getValue() != PermissionGrantResult.Undefined).collect(Collectors.toList())) {
                result.getResult().put(grantResult.getKey(), grantResult.getValue());
                permissionDefinitions.removeIf(x -> x.getName().equals(grantResult.getKey()));
            }
            if (result.allGranted() || result.allProhibited()) {
                break;
            }
        }

        return result;
    }

    public IPermissionDefinitionManager getPermissionDefinitionManager() {
        return permissionDefinitionManager;
    }

    public ICurrentPrincipalAccessor getPrincipalAccessor() {
        return principalAccessor;
    }

    public ICurrentTenant getCurrentTenant() {
        return currentTenant;
    }

    public IPermissionValueProviderManager getPermissionValueProviderManager() {
        return permissionValueProviderManager;
    }

    public ISimpleStateCheckerManager<PermissionDefinition> getStateCheckerManager() {
        return stateCheckerManager;
    }
}
