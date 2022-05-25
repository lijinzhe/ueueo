package com.ueueo.tenant.management.domain;

import com.ueueo.ID;
import com.ueueo.UID;
import com.ueueo.ddd.domain.entities.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-05-18 15:51
 */
@Getter
public class TenantConnectionString extends Entity {
    @Setter(AccessLevel.PROTECTED)
    private ID tenantId;
    @Setter(AccessLevel.PROTECTED)
    private String name;

    private String value;

    protected TenantConnectionString() {}

    public TenantConnectionString(ID tenantId, @NonNull String name, @NonNull String value) {
        this.tenantId = tenantId;
        Assert.isTrue(StringUtils.isNotBlank(name), "name must not null!");
        Assert.isTrue(name.length() <= TenantConnectionStringConsts.MAX_NAME_LENGTH,
                String.format("name length must <= %s!", TenantConnectionStringConsts.MAX_NAME_LENGTH));
        this.name = name;
        setValue(value);
    }

    public void setValue(@NonNull String value) {
        Assert.isTrue(StringUtils.isNotBlank(value), "value must not null!");
        Assert.isTrue(value.length() <= TenantConnectionStringConsts.MAX_VALUE_LENGTH,
                String.format("value length must <= %s!", TenantConnectionStringConsts.MAX_VALUE_LENGTH));
        this.value = value;
    }

    @Override
    public ID getId() {
        return UID.valueOf(tenantId, name);
    }
}
