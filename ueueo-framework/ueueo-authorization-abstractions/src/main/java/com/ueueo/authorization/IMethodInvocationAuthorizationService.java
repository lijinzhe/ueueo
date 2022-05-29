package com.ueueo.authorization;

/**
 *
 * @author Lee
 * @date 2022-05-29 13:38
 */
public interface IMethodInvocationAuthorizationService {
    void check(MethodInvocationAuthorizationContext context);
}
