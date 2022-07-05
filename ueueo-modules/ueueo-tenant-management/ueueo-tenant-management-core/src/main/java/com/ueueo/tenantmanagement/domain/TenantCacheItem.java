package com.ueueo.tenantmanagement.domain;

import com.ueueo.exception.BaseException;
import com.ueueo.ID;
import com.ueueo.multitenancy.IgnoreMultiTenancyAttribute;
import com.ueueo.multitenancy.TenantConfiguration;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lee
 * @date 2022-05-19 23:03
 */
@IgnoreMultiTenancyAttribute
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
            throw new BaseException("Both id and name can't be invalid.");
        }
        return String.format(CacheKeyFormat, id != null ? id.toString() : "null", StringUtils.isBlank(name) ? "null" : name);
    }
}
