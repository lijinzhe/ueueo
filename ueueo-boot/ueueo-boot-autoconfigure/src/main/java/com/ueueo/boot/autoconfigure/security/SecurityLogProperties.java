package com.ueueo.boot.autoconfigure.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Lee
 * @date 2022-05-17 10:43
 */
@Data
@ConfigurationProperties(prefix = "ueueo.security.log")
public class SecurityLogProperties {
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
