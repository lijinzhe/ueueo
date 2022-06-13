package com.ueueo.authorization.microsoft;

/**
 * Assembly Microsoft.AspNetCore.Authorization, Version=6.0.0.0, Culture=neutral, PublicKeyToken=adb9793829ddae60
 * Microsoft.AspNetCore.Authorization.dll
 *
 * Classes implementing this interface are able to make a decision if authorization
 * is allowed.
 *
 * @author Lee
 * @date 2022-06-13 20:35
 */
public interface IAuthorizationHandler {

    /**
     * Makes a decision if authorization is allowed.
     *
     * @param context The authorization information.
     */
    void handle(AuthorizationHandlerContext context);
}
