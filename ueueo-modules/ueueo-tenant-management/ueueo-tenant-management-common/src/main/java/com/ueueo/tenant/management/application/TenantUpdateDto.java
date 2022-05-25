package com.ueueo.tenant.management.application;

import com.ueueo.ddd.domain.entities.IHasConcurrencyStamp;
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
