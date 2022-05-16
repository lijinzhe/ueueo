package com.ueueo.clients;

import com.ueueo.principal.ClaimsPrincipal;
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
    public String getId() {
        return Optional.ofNullable(principalAccessor.getPrincipal())
                .map(ClaimsPrincipal::findClientId)
                .orElse(null);
    }

    @Override
    public boolean isAuthenticated() {
        return getId() != null;
    }

}
