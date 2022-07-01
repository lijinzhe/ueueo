package com.ueueo.authorization.microsoft;

import com.ueueo.security.principal.ClaimsPrincipal;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO
 * Assembly Microsoft.AspNetCore.Authorization, Version=6.0.0.0, Culture=neutral, PublicKeyToken=adb9793829ddae60
 *  Microsoft.AspNetCore.Authorization.dll
 *
 * Contains authorization information used by Microsoft.AspNetCore.Authorization.IAuthorizationHandler.
 *
 * @author Lee
 * @date 2022-06-13 20:37
 */
public class AuthorizationHandlerContext {

    /**
     * Creates a new instance of Microsoft.AspNetCore.Authorization.AuthorizationHandlerContext.
     *
     * @param requirements A collection of all the Microsoft.AspNetCore.Authorization.IAuthorizationRequirement
     *                     for the current authorization action.
     * @param user         A System.Security.Claims.ClaimsPrincipal representing the current user.
     * @param resource     An optional resource to evaluate the requirements against.
     */
    public AuthorizationHandlerContext(Collection<IAuthorizationRequirement> requirements, ClaimsPrincipal user, Object resource) {
        this.requirements = requirements;
        this.user = user;
        this.resource = resource;
    }

    /**
     * The collection of all the Microsoft.AspNetCore.Authorization.IAuthorizationRequirement
     * for the current authorization action.
     */
    private Collection<IAuthorizationRequirement> requirements;
    /**
     * The System.Security.Claims.ClaimsPrincipal representing the current user.
     */
    private ClaimsPrincipal user;

    /**
     * The optional resource to evaluate the Microsoft.AspNetCore.Authorization.AuthorizationHandlerContext.Requirements
     * against.
     */
    private Object resource;

    /**
     * Gets the requirements that have not yet been marked as succeeded.
     */
    private Collection<IAuthorizationRequirement> pendingRequirements;

    /**
     * Gets the reasons why authorization has failed.
     */
    private Collection<AuthorizationFailureReason> failureReasons;

    /**
     * Flag indicating whether the current authorization processing has failed.
     */
    private boolean hasFailed;

    /**
     * Flag indicating whether the current authorization processing has succeeded.
     */
    private boolean hasSucceeded;

    /**
     * Called to indicate Microsoft.AspNetCore.Authorization.AuthorizationHandlerContext.HasSucceeded
     * will never return true, even if all requirements are met.
     */
    public void fail() {
        hasFailed = true;
        hasSucceeded = false;
    }

    /**
     * Called to indicate Microsoft.AspNetCore.Authorization.AuthorizationHandlerContext.HasSucceeded
     * will never return true, even if all requirements are met.
     *
     * @param reason Optional Microsoft.AspNetCore.Authorization.AuthorizationFailureReason for why
     *               authorization failed.
     */
    public void fail(AuthorizationFailureReason reason) {
        fail();
        failureReasons = new ArrayList<>();
        failureReasons.add(reason);
    }

    /**
     * Called to mark the specified requirement as being successfully evaluated.
     *
     * @param requirement The requirement whose evaluation has succeeded.
     */
    public void succeed(IAuthorizationRequirement requirement) {
        hasFailed = false;
        hasSucceeded = true;
        requirements = new ArrayList<>();
        requirements.add(requirement);
    }

    public Collection<IAuthorizationRequirement> getRequirements() {
        return requirements;
    }

    public ClaimsPrincipal getUser() {
        return user;
    }

    public Object getResource() {
        return resource;
    }

    public Collection<IAuthorizationRequirement> getPendingRequirements() {
        return pendingRequirements;
    }

    public Collection<AuthorizationFailureReason> getFailureReasons() {
        return failureReasons;
    }

    public boolean isHasFailed() {
        return hasFailed;
    }

    public boolean isHasSucceeded() {
        return hasSucceeded;
    }
}
