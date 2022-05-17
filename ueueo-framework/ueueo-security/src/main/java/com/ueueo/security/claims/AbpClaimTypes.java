package com.ueueo.security.claims;

import com.ueueo.claims.ClaimTypes;

/**
 * TODO Instead of directly using static properties, can we just create an AbpClaimOptions and pass these values as defaults?
 *
 * Used to get ABP-specific claim type names.
 *
 * @author Lee
 * @date 2022-05-14 11:15
 */
public class AbpClaimTypes {
    /**
     * Default: {@link ClaimTypes#Name}
     */
    public static String UserName = ClaimTypes.Name;

    /**
     * Default: {@link ClaimTypes#GivenName}
     */
    public static String Name = ClaimTypes.GivenName;

    /**
     * Default: {@link ClaimTypes#Surname}
     */
    public static String SurName = ClaimTypes.Surname;

    /**
     * Default: {@link ClaimTypes#NameIdentifier}
     */
    public static String UserId = ClaimTypes.NameIdentifier;

    /**
     * Default: {@link ClaimTypes#Role}
     */
    public static String Role = ClaimTypes.Role;

    /**
     * Default: {@link ClaimTypes#Email}
     */
    public static String Email = ClaimTypes.Email;

    /**
     * Default: "email_verified".
     */
    public static String EmailVerified = "email_verified";

    /**
     * Default: "phone_number".
     */
    public static String PhoneNumber = "phone_number";

    /**
     * Default: "phone_number_verified".
     */
    public static String PhoneNumberVerified = "phone_number_verified";

    /**
     * Default: "tenantid".
     */
    public static String TenantId = "tenantid";

    /**
     * Default: "editionid".
     */
    public static String EditionId = "editionid";

    /**
     * Default: "client_id".
     */
    public static String ClientId = "client_id";

    /**
     * Default: "impersonator_tenantid".
     */
    public static String ImpersonatorTenantId = "impersonator_tenantid";

    /**
     * Default: "impersonator_userid".
     */
    public static String ImpersonatorUserId = "impersonator_userid";

    /**
     * Default: "impersonator_tenantname".
     */
    public static String ImpersonatorTenantName = "impersonator_tenantname";

    /**
     * Default: "impersonator_username".
     */
    public static String ImpersonatorUserName = "impersonator_username";
}
