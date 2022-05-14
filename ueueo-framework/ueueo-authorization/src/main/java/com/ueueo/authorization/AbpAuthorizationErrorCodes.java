package com.ueueo.authorization;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:14
 */
public class AbpAuthorizationErrorCodes {
    public static final String GivenPolicyHasNotGranted = "Volo.Authorization:010001";

    public static final String GivenPolicyHasNotGrantedWithPolicyName = "Volo.Authorization:010002";

    public static final String GivenPolicyHasNotGrantedForGivenResource = "Volo.Authorization:010003";

    public static final String GivenRequirementHasNotGrantedForGivenResource = "Volo.Authorization:010004";

    public static final String GivenRequirementsHasNotGrantedForGivenResource = "Volo.Authorization:010005";
}
