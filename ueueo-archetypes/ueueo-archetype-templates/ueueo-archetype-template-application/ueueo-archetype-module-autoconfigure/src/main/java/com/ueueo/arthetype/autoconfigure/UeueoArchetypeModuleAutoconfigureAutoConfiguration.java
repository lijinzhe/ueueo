package com.ueueo.arthetype.autoconfigure;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lee
 * @date 2018/11/24
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(UeueoArchetypeModuleAutoconfigureProperties.class)
@ConditionalOnProperty(value = "com.ueueo.archetype", havingValue = "true", matchIfMissing = true)
public class UeueoArchetypeModuleAutoconfigureAutoConfiguration {

}
