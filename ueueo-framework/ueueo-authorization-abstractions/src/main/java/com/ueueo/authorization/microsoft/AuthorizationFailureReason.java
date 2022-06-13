package com.ueueo.authorization.microsoft;

/**
 * Encapsulates a reason why authorization failed.
 *
 * @author Lee
 * @date 2022-06-13 20:47
 */
public class AuthorizationFailureReason {

    /**
     * A message describing the failure reason.
     */
    private String message;

    /**
     * The Microsoft.AspNetCore.Authorization.IAuthorizationHandler responsible for
     * this failure reason.
     */
    private IAuthorizationHandler handler;

    /**
     * Creates a new failure reason.
     *
     * @param message The handler responsible for this failure reason.
     * @param handler The message describing the failure.
     */
    public AuthorizationFailureReason(String message, IAuthorizationHandler handler) {
        this.message = message;
        this.handler = handler;
    }

    public String getMessage() {
        return message;
    }

    public IAuthorizationHandler getHandler() {
        return handler;
    }
}
