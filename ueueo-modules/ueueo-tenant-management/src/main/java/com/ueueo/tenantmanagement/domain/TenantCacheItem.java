package com.ueueo.tenantmanagement.domain;

import com.ueueo.AbpException;
import com.ueueo.ID;
import com.ueueo.multitenancy.IgnoreMultiTenancy;
import com.ueueo.multitenancy.TenantConfiguration;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lee
 * @date 2022-05-19 23:03
 */
@IgnoreMultiTenancy
@Data
public class TenantCacheItem {
    private static final String CacheKeyFormat = "i:%s,n:%s}";

    private TenantConfiguration value;

    public TenantCacheItem() {
    }

    public TenantCacheItem(TenantConfiguration value) {
        this.value = value;
    }

    public static String calculateCacheKey(ID id, String name) {
        if (id == null && StringUtils.isBlank(name)) {
            throw new AbpException("Both id and name can't be invalid.");
        }
        return String.format(CacheKeyFormat, id != null ? id.toString() : "null", StringUtils.isBlank(name) ? "null" : name);
    }
}
