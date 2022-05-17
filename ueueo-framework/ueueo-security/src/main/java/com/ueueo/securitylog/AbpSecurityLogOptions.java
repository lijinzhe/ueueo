package com.ueueo.securitylog;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-17 10:43
 */
@Data
public class AbpSecurityLogOptions {
    /**
     * Default: true.
     */
    private boolean isEnable = true;
    /**
     * The name of the application or service writing security log.
     * Default: null.
     */
    private String applicationName;

}
