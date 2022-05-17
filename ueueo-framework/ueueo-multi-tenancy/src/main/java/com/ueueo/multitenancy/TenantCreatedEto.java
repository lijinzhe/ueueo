package com.ueueo.multitenancy;

import com.ueueo.domain.entities.events.distributed.EtoBase;
import com.ueueo.eventbus.EventName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lee
 * @date 2022-05-14 17:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@EventName(name = "abp.multi_tenancy.tenant.created")
public class TenantCreatedEto extends EtoBase {
    private Long id;
    private String name;
}
