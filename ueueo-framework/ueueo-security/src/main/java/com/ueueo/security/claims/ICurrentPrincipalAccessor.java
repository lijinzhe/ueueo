package com.ueueo.security.claims;

import com.ueueo.disposable.IDisposable;
import com.ueueo.security.principal.ClaimsPrincipal;

import java.util.Collections;
import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 21:06
 */
public interface ICurrentPrincipalAccessor {
    ClaimsPrincipal getCurrentPrincipal();

    IDisposable change(ClaimsPrincipal principal);

    default void change(Claim claim) {
        change(Collections.singletonList(claim));
    }

    default void change(List<Claim> claims) {
        change(new ClaimsIdentity(claims));
    }

    default void change(ClaimsIdentity claimsIdentity) {
        change(new ClaimsPrincipal(claimsIdentity));
    }
}
