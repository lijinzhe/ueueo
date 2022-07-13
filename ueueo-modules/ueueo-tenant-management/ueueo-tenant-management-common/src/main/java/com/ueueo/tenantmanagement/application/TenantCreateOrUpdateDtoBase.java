package com.ueueo.tenantmanagement.application;

import com.ueueo.validation.annotations.Display;
import com.ueueo.validation.annotations.MaxLength;
import com.ueueo.validation.annotations.Required;
import com.ueueo.data.objectextending.ExtensibleObject;
import com.ueueo.tenantmanagement.domain.TenantConsts;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lee
 * @date 2022-05-20 14:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenantCreateOrUpdateDtoBase extends ExtensibleObject {
    @Required
    @MaxLength(TenantConsts.MAX_NAME_LENGTH)
    @Display(name = "TenantName")
    private String name;

    public TenantCreateOrUpdateDtoBase() {
        super();
    }
}
