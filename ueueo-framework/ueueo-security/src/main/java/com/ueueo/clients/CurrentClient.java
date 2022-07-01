package com.ueueo.clients;

import com.ueueo.ID;
import com.ueueo.security.principal.ClaimsPrincipal;
import com.ueueo.security.claims.ICurrentPrincipalAccessor;

import java.util.Optional;

/**
 * @author Lee
 * @date 2021-08-26 20:58
 */
public class CurrentClient implements ICurrentClient {

    private final ICurrentPrincipalAccessor principalAccessor;

    public CurrentClient(ICurrentPrincipalAccessor principalAccessor) {
        this.principalAccessor = principalAccessor;
    }

    @Override
    public ID getId() {
        return Optional.ofNullable(principalAccessor.getCurrentPrincipal())
                .map(ClaimsPrincipal::findClientId)
                .orElse(null);
    }

    @Override
    public boolean isAuthenticated() {
        return getId() != null;
    }

}
