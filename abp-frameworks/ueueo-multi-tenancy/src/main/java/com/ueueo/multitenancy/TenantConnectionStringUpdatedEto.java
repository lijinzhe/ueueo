package com.ueueo.multitenancy;

import com.ueueo.ID;
import com.ueueo.domain.entities.events.distributed.EtoBase;
import com.ueueo.eventbus.EventNameAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lee
 * @date 2022-05-14 17:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@EventNameAttribute(name = "abp.multi_tenancy.tenant.connection_string.updated")
public class TenantConnectionStringUpdatedEto extends EtoBase {
    private ID id;
    private String name;
    private String connectionStringName;
    private String oldValue;
    private String newValue;
}
