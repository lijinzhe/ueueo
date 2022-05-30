package com.ueueo.multitenancy;

import com.ueueo.ID;
import com.ueueo.domain.entities.events.distributed.EtoBase;
import com.ueueo.eventbus.EventNameAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-14 17:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@EventNameAttribute(name = "abp.multi_tenancy.tenant.created")
public class TenantCreatedEto extends EtoBase {
    private ID id;
    private String name;

    public TenantCreatedEto(ID id, String name, Map<String, String> properties) {
        this.id = id;
        this.name = name;
        this.properties = properties;
    }
}
