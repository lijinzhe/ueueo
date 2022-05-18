package com.ueueo.users;

import com.ueueo.ID;
import com.ueueo.claims.Claim;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2021-08-19 21:53
 */
public interface ICurrentUser {

    boolean getIsAuthenticated();

    @Nullable
    ID getId();

    @Nullable
    String getUserName();

    @Nullable
    String getName();

    @Nullable
    String getSurName();

    @Nullable
    String getPhoneNumber();

    boolean getPhoneNumberVerified();

    @Nullable
    String getEmail();

    boolean getEmailVerified();

    ID getTenantId();

    List<String> getRoles();

    @Nullable
    Claim findClaim(String claimType);

    @NonNull
    List<Claim> findClaims(String claimType);

    @NonNull
    List<Claim> getAllClaims();

    boolean isInRole(String roleName);
}
