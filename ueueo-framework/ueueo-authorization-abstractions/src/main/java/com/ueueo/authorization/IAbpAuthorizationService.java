package com.ueueo.authorization;

import com.ueueo.principal.ClaimsPrincipal;

/**
 * Checks policy based permissions for a user
 *
 * @author Lee
 * @date 2021-08-26 20:01
 */
public interface IAbpAuthorizationService {

    ClaimsPrincipal getCurrentPrincipal();


     boolean isGranted(String policyName);
}
