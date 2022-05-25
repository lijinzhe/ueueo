package com.ueueo.tenant.management.application;

import com.ueueo.data.annotations.Display;
import com.ueueo.data.annotations.MaxLength;
import com.ueueo.data.annotations.Required;
import com.ueueo.data.objectextending.ExtensibleObject;
import com.ueueo.tenant.management.domain.TenantConsts;
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
