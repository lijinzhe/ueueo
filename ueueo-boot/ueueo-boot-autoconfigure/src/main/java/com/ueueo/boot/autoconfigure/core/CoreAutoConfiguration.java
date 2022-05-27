package com.ueueo.boot.autoconfigure.core;

import com.ueueo.tracing.DefaultCorrelationIdProvider;
import com.ueueo.tracing.ICorrelationIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lee
 * @date 2022-05-27 17:57
 */
@Configuration
public class CoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ICorrelationIdProvider.class)
    public ICorrelationIdProvider correlationIdProvider() {
        return new DefaultCorrelationIdProvider();
    }
}
