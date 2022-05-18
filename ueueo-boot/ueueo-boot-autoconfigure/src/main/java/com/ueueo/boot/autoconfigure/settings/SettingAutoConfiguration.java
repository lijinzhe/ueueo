package com.ueueo.boot.autoconfigure.settings;

import com.ueueo.settings.AbpSettingOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-18 14:42
 */
@Configuration
@ConditionalOnClass(AbpSettingOptions.class)
public class SettingAutoConfiguration {

}
