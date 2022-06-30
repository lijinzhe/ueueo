package com.ueueo.boot.autoconfigure.web.multitenancy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ueueo.web.multi-tenancy")
public class WebMultiTenancyConfigProperties {

    /**
     * Default DefaultTenantField
     */
    private String tenantField;

    private String domainRegex;

}
