package com.ueueo.authorization.microsoft;

/**
 * Base class for authorization handlers that need to be called for a specific requirement
 * type.
 *
 * @param <TRequirement> The type of the requirement to handle.
 *
 * @author Lee
 * @date 2022-06-13 20:37
 */
public abstract class AuthorizationHandler<TRequirement extends IAuthorizationRequirement> implements IAuthorizationHandler {

    @Override
    public void handle(AuthorizationHandlerContext context) {

    }

    /**
     * Makes a decision if authorization is allowed based on a specific requirement.
     *
     * @param context     The authorization context.
     * @param requirement The requirement to evaluate.
     */
    protected abstract void handleRequirement(AuthorizationHandlerContext context, TRequirement requirement);
}
