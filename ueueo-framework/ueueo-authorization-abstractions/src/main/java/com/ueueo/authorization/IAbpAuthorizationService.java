package com.ueueo.authorization;

import java.security.Principal;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 20:01
 */
public interface IAbpAuthorizationService {
    Principal getCurrentPrincipal();
}
