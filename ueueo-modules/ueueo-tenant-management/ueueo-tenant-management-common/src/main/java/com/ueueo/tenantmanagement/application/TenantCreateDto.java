package com.ueueo.tenantmanagement.application;

import com.ueueo.auditing.DisableAuditing;
import com.ueueo.validation.annotations.Email;
import com.ueueo.validation.annotations.MaxLength;
import com.ueueo.validation.annotations.Required;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lee
 * @date 2022-05-20 13:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenantCreateDto extends TenantCreateOrUpdateDtoBase {

    @Required
    @Email
    @MaxLength(256)
    private String adminEmailAddress;

    @Required
    @MaxLength(128)
    @DisableAuditing
    private String adminPassword;
}
