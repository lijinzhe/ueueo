package com.ueueo.security.claims;

import com.ueueo.principal.ClaimsPrincipal;

import java.util.concurrent.Future;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:02
 */
public interface IAbpClaimsPrincipalFactory {
    Future<ClaimsPrincipal> createAsync(ClaimsPrincipal existsClaimsPrincipal);
}
