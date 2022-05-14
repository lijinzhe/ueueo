package com.ueueo.security.security.claims;

import java.security.Principal;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:02
 */
public interface IAbpClaimsPrincipalFactory {
    Principal create(Principal existsClaimsPrincipal);
}
