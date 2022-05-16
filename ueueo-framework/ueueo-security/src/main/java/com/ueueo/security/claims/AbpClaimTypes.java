package com.ueueo.security.claims;

/**
 * TODO Instead of directly using static properties, can we just create an AbpClaimOptions and pass these values as defaults?
 *
 * Used to get ABP-specific claim type names.
 *
 * @author Lee
 * @date 2022-05-14 11:15
 */
public class AbpClaimTypes {
    /// <summary>
    /// Default: <see cref="ClaimTypes.Name"/>
    /// </summary>
    public static String UserName  = ClaimTypes.Name;

    /// <summary>
    /// Default: <see cref="ClaimTypes.GivenName"/>
    /// </summary>
    public static String Name  = ClaimTypes.GivenName;

    /// <summary>
    /// Default: <see cref="ClaimTypes.Surname"/>
    /// </summary>
    public static String SurName  = ClaimTypes.Surname;

    /// <summary>
    /// Default: <see cref="ClaimTypes.NameIdentifier"/>
    /// </summary>
    public static String UserId  = ClaimTypes.NameIdentifier;

    /// <summary>
    /// Default: <see cref="ClaimTypes.Role"/>
    /// </summary>
    public static String Role  = ClaimTypes.Role;

    /// <summary>
    /// Default: <see cref="ClaimTypes.Email"/>
    /// </summary>
    public static String Email  = ClaimTypes.Email;

    /// <summary>
    /// Default: "email_verified".
    /// </summary>
    public static String EmailVerified = "email_verified";

    /// <summary>
    /// Default: "phone_number".
    /// </summary>
    public static String PhoneNumber= "phone_number";

    /// <summary>
    /// Default: "phone_number_verified".
    /// </summary>
    public static String PhoneNumberVerified= "phone_number_verified";

    /// <summary>
    /// Default: "tenantid".
    /// </summary>
    public static String TenantId= "tenantid";

    /// <summary>
    /// Default: "editionid".
    /// </summary>
    public static String EditionId= "editionid";

    /// <summary>
    /// Default: "client_id".
    /// </summary>
    public static String ClientId= "client_id";

    /// <summary>
    /// Default: "impersonator_tenantid".
    /// </summary>
    public static String ImpersonatorTenantId= "impersonator_tenantid";

    /// <summary>
    /// Default: "impersonator_userid".
    /// </summary>
    public static String ImpersonatorUserId= "impersonator_userid";

    /// <summary>
    /// Default: "impersonator_tenantname".
    /// </summary>
    public static String ImpersonatorTenantName= "impersonator_tenantname";

    /// <summary>
    /// Default: "impersonator_username".
    /// </summary>
    public static String ImpersonatorUserName= "impersonator_username";
}
