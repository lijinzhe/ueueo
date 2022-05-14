package com.ueueo.security.users;

import com.ueueo.security.security.claims.ICurrentPrincipalAccessor;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2021-09-13 14:04
 */
@Data
public class CurrentUser implements ICurrentUser {

    private static final Collection<Claim> EmptyClaimsArray = Collections.emptyList();

    private ICurrentPrincipalAccessor currentPrincipalAccessor;

    @Override
    public boolean getIsAuthenticated() {
        return false;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getSurName() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public boolean getPhoneNumberVerified() {
        return false;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public boolean getEmailVerified() {
        return false;
    }

    @Override
    public Long getTenantId() {
        return null;
    }

    @Override
    public Collection<String> getRoles() {
        return null;
    }

    @Override
    public Claim findClaim(String claimType) {
        return null;
    }

    @Override
    public Collection<Claim> findClaims(String claimType) {
        return null;
    }

    @Override
    public Collection<Claim> getAllClaims() {
        return null;
    }

    @Override
    public boolean isInRole(String roleName) {
        return false;
    }
}
