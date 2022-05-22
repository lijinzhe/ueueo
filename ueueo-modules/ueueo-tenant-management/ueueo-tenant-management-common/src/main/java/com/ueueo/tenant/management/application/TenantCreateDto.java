package com.ueueo.tenant.management.application;

import lombok.Data;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 13:49
 */
@Data
public class TenantCreateDto {
    //            [Required]
    //            [EmailAddress]
    //            [MaxLength(256)]
    private String adminEmailAddress;
    //    [Required]
    //            [MaxLength(128)]
    //            [DisableAuditing]
    private String adminPassword;
}
