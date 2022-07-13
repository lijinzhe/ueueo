package com.ueueo.authorization;

import com.ueueo.security.claims.ICurrentPrincipalAccessor;
import com.ueueo.security.principal.ClaimsPrincipal;
import org.springframework.security.core.Authentication;

/**
 * @author Lee
 * @date 2021-08-26 20:01
 */
public class AlwaysAllowAuthorizationService implements IAuthorizationService {

    private final ICurrentPrincipalAccessor currentPrincipalAccessor;

    public AlwaysAllowAuthorizationService(ICurrentPrincipalAccessor currentPrincipalAccessor) {
        this.currentPrincipalAccessor = currentPrincipalAccessor;
    }

    @Override
    public ClaimsPrincipal getCurrentPrincipal() {
        return currentPrincipalAccessor.getCurrentPrincipal();
    }

    @Override
    public boolean isGranted(String policyName) {
        return true;
    }
}
