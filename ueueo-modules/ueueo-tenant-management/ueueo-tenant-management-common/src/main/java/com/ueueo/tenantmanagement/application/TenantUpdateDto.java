package com.ueueo.tenantmanagement.application;

import com.ueueo.ddd.domain.IHasConcurrencyStamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lee
 * @date 2022-05-20 13:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenantUpdateDto extends TenantCreateOrUpdateDtoBase implements IHasConcurrencyStamp {
    private String concurrencyStamp;
}
