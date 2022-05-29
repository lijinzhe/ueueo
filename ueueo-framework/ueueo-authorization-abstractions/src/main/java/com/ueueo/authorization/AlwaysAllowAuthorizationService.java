package com.ueueo.authorization;

import com.ueueo.principal.ClaimsPrincipal;
import com.ueueo.security.claims.ICurrentPrincipalAccessor;

/**
 * @author Lee
 * @date 2021-08-26 20:01
 */
public class AlwaysAllowAuthorizationService implements IAbpAuthorizationService {

    public ClaimsPrincipal currentPrincipal;

    private ICurrentPrincipalAccessor currentPrincipalAccessor;

    public AlwaysAllowAuthorizationService(ICurrentPrincipalAccessor currentPrincipalAccessor) {
        this.currentPrincipalAccessor = currentPrincipalAccessor;
        this.currentPrincipal = currentPrincipalAccessor.getPrincipal();
    }

    @Override
    public ClaimsPrincipal getCurrentPrincipal() {
        return currentPrincipal;
    }

}
