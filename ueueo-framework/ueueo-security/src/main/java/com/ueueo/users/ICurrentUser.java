package com.ueueo.users;

import com.ueueo.claims.Claim;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2021-08-19 21:53
 */
public interface ICurrentUser {

    boolean getIsAuthenticated();

    @Nullable
    Long getId();

    @Nullable
    String getUserName();

    @Nullable
    String getSurName();

    @Nullable
    String getPhoneNumber();

    boolean getPhoneNumberVerified();

    @Nullable
    String getEmail();

    boolean getEmailVerified();

    Long getTenantId();

    Collection<String> getRoles();

    @Nullable
    Claim findClaim(String claimType);

    @NonNull
    Collection<Claim> findClaims(String claimType);

    @NonNull
    Collection<Claim> getAllClaims();

    boolean isInRole(String roleName);
}
