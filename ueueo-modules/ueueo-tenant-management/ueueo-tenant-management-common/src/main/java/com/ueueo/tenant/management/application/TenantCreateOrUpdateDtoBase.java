package com.ueueo.tenant.management.application;

import com.ueueo.data.annotations.Display;
import com.ueueo.data.annotations.MaxLength;
import com.ueueo.data.annotations.Required;
import com.ueueo.data.objectextending.ExtensibleObject;
import com.ueueo.tenant.management.domain.TenantConsts;
import lombok.Data;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 14:10
 */
@Data
public class TenantCreateOrUpdateDtoBase extends ExtensibleObject {
    @Required
    @MaxLength(TenantConsts.MaxNameLength)
    @Display(name = "TenantName")
    private String name;

    public TenantCreateOrUpdateDtoBase() {
        super();
    }
}
