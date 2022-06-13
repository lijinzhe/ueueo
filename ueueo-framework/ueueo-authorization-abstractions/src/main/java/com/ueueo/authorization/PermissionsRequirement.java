package com.ueueo.authorization;

import com.ueueo.authorization.microsoft.IAuthorizationRequirement;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

@Getter
public class PermissionsRequirement implements IAuthorizationRequirement {
    private List<String> permissionNames;

    private boolean requiresAll;

    public PermissionsRequirement(@NonNull List<String> permissionNames, boolean requiresAll) {
        Objects.requireNonNull(permissionNames);

        this.permissionNames = permissionNames;
        this.requiresAll = requiresAll;
    }

    @Override
    public String toString() {
        return String.format("PermissionsRequirement: %s", StringUtils.join(permissionNames, ", "));
    }
}
