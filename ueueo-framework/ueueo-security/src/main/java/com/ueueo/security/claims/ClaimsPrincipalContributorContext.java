package com.ueueo.security.claims;

import com.ueueo.security.principal.ClaimsPrincipal;
import lombok.Getter;

/**
 * TODO
 *
 * @author Lee
 * @date 2021-08-26 21:01
 */
public class ClaimsPrincipalContributorContext {
    @Getter
    private final ClaimsPrincipal claimsPrincipal;

    public ClaimsPrincipalContributorContext(ClaimsPrincipal claimsPrincipal) {
        this.claimsPrincipal = claimsPrincipal;
    }
}
