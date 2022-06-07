package com.ueueo.tenantmanagement.localization;

import com.ueueo.localization.LocalizationResourceNameAttribute;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author Lee
 * @date 2022-06-07 10:43
 */
@LocalizationResourceNameAttribute(name = "TenantManagement")
public class TenantManagementMessageSource extends ResourceBundleMessageSource {

    public static final String BASENAME = "localization.tenant-management";

    public TenantManagementMessageSource() {
        setBasename(BASENAME);
    }

}
