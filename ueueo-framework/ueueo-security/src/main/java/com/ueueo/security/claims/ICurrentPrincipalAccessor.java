package com.ueueo.security.claims;

import com.ueueo.claims.Claim;
import com.ueueo.claims.ClaimsIdentity;
import com.ueueo.principal.ClaimsPrincipal;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Lee
 * @date 2021-08-26 21:06
 */
public interface ICurrentPrincipalAccessor {
    ClaimsPrincipal getPrincipal();

    void change(ClaimsPrincipal principal);

    default void change(Claim claim) {
        change(Collections.singleton(claim));
    }

    default void change(Collection<Claim> claims) {
        change(new ClaimsIdentity(claims));
    }

    default void change(ClaimsIdentity claimsIdentity) {
        change(new ClaimsPrincipal(claimsIdentity));
    }
}
