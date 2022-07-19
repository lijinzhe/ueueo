package com.ueueo.arthetype.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Lee
 * @date 2018/11/24
 */
@Data
@ConfigurationProperties(prefix = "com.ueueo.archetype")
public class UeueoArchetypeModuleAutoconfigureProperties {
    /**
     * 是否开启
     */
    private boolean enable = true;

}
