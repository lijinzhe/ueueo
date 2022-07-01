package com.ueueo.security.claims;

import com.ueueo.ID;
import com.ueueo.security.principal.IIdentity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-16 20:48
 */
public class ClaimsIdentity implements IIdentity {
    //    public static final String DefaultIssuer = "LOCAL AUTHORITY";
    //    public static final String DefaultNameClaimType = "name";
    //    public static final String DefaultRoleClaimType = "role";

    @Getter
    private String authenticationType;
    @Getter
    private String name;
    @Getter
    @Setter
    private String label;

    private boolean isAuthenticated = false;
    @Getter
    private List<Claim> claims = new ArrayList<>();
    @Getter
    @Setter
    private Object bootstrapContext;
    @Getter
    @Setter
    private ClaimsIdentity actor;
    @Getter
    private String roleClaimType;
    @Getter
    private String nameClaimType;

    public ClaimsIdentity() {

    }

    public ClaimsIdentity(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public ClaimsIdentity(IIdentity identity) {
        this.name = identity.getName();
        this.authenticationType = identity.getAuthenticationType();
        this.isAuthenticated = identity.getIsAuthenticated();
    }

    public ClaimsIdentity(@NonNull List<Claim> claims) {
        this.claims = claims;
    }

    public ClaimsIdentity(@NonNull List<Claim> claims, String authenticationType) {
        this.claims = claims;
        this.authenticationType = authenticationType;
    }

    public ClaimsIdentity(IIdentity identity, @NonNull List<Claim> claims) {
        this.name = identity.getName();
        this.authenticationType = identity.getAuthenticationType();
        this.isAuthenticated = identity.getIsAuthenticated();
        this.claims = claims;
    }

    public ClaimsIdentity(String authenticationType, String nameType, String roleType) {
        this.authenticationType = authenticationType;
        this.nameClaimType = nameType;
        this.roleClaimType = roleType;
    }

    public ClaimsIdentity(@NonNull List<Claim> claims, String authenticationType, String nameType, String roleType) {
        this.claims = claims;
        this.authenticationType = authenticationType;
        this.nameClaimType = nameType;
        this.roleClaimType = roleType;
    }

    public ClaimsIdentity(IIdentity identity, @NonNull List<Claim> claims, String authenticationType, String nameType, String roleType) {
        this.name = identity.getName();
        this.authenticationType = identity.getAuthenticationType();
        this.isAuthenticated = identity.getIsAuthenticated();
        this.claims = claims;
        this.authenticationType = authenticationType;
        this.nameClaimType = nameType;
        this.roleClaimType = roleType;
    }

    protected ClaimsIdentity(ClaimsIdentity other) {

    }

    public void addClaim(@NonNull Claim claim) {
        this.claims.add(claim);
    }

    public ClaimsIdentity addIfNotContains(@NonNull Claim claim) {
        Assert.notNull(claim, "claim must not null.");
        if (this.claims.stream().noneMatch(c -> StringUtils.equalsIgnoreCase(claim.getType(), c.getType()))) {
            addClaim(claim);
        }
        return this;
    }

    public ClaimsIdentity addOrReplace(@NonNull Claim claim) {
        Assert.notNull(claim, "claim must not null.");
        this.claims.removeIf(c -> StringUtils.equalsIgnoreCase(claim.getType(), c.getType()));
        addClaim(claim);
        return this;
    }

    public void addClaims(List<Claim> claims) {
        this.claims.addAll(claims);
    }

    @Override
    public ClaimsIdentity clone() {
        return null;
    }

    public List<Claim> findAll(String type) {
        return claims.stream().filter(claim -> claim.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    public List<Claim> findAll(Predicate<Claim> match) {
        return claims.stream().filter(match).collect(Collectors.toList());
    }

    public Claim findFirst(Predicate<Claim> match) {
        return claims.stream().filter(match).findFirst().orElse(null);
    }

    public Claim findFirst(String type) {
        return claims.stream().filter(claim -> claim.getType().equalsIgnoreCase(type)).findFirst().orElse(null);
    }

    public boolean hasClaim(Predicate<Claim> match) {
        return claims.stream().anyMatch(match);
    }

    public boolean hasClaim(String type, String value) {
        return claims.stream().anyMatch(claim -> StringUtils.equalsIgnoreCase(claim.getType(), type) && Objects.equals(claim.getValue(), value));
    }

    public void removeClaim(Claim claim) {
        this.claims.remove(claim);
    }

    public boolean tryRemoveClaim(Claim claim) {
        return this.claims.remove(claim);
    }

    @Override
    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public ID findUserId() {
        return findClaimIDValue(ClaimTypes.UserId);
    }

    public ID findTenantId() {
        return findClaimIDValue(ClaimTypes.TenantId);
    }

    public String findClientId() {
        return findClaimValue(ClaimTypes.ClientId);
    }

    public ID findEditionId() {
        return findClaimIDValue(ClaimTypes.EditionId);
    }

    public ID findImpersonatorTenantId() {
        return findClaimIDValue(ClaimTypes.ImpersonatorTenantId);
    }

    public ID findImpersonatorUserId() {
        return findClaimIDValue(ClaimTypes.ImpersonatorUserId);
    }

    private String findClaimValue(String claimType) {
        return claims.stream()
                .filter(claim -> StringUtils.equalsIgnoreCase(claim.getType(), claimType)).findFirst()
                .map(Claim::getValue)
                .orElse(null);
    }

    private ID findClaimIDValue(String claimType) {
        return claims.stream()
                .filter(claim -> StringUtils.equalsIgnoreCase(claim.getType(), claimType)).findFirst()
                .map(claim -> {
                    try {
                        return ID.valueOf(claim.getValue());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .orElse(null);
    }

}
