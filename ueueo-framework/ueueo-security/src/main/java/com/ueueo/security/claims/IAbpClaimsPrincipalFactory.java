package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2021-08-26 21:02
 */
public interface IAbpClaimsPrincipalFactory {
    Future<ClaimsPrincipal> createAsync(ClaimsPrincipal existsClaimsPrincipal);
}
