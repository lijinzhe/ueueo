package com.ueueo.boot.autoconfigure.distributedlocking;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lee
 * @date 2022-05-27 17:57
 */
@Configuration
@EnableConfigurationProperties(RedisDistributedLockProperties.class)
public class DistributedLockingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    @ConditionalOnClass(RedissonClient.class)
    public RedissonClient redissonClient(RedisDistributedLockProperties options) {
        Config config = new Config();
        if (RedisServerType.Sentinel.equals(options.getServerType())) {
            SentinelServersConfig serverConfig = config.useSentinelServers()
                    .addSentinelAddress(options.getSentinelAddresses())
                    .setMasterName(options.getMasterName())
                    .setDatabase(options.getDatabase());
            if (options.getPassword() != null && !options.getPassword().isEmpty()) {
                serverConfig.setPassword(options.getPassword());
            }
            return Redisson.create(config);
        } else {
            SingleServerConfig serverConfig = config.useSingleServer()
                    .setAddress(options.getAddress())
                    .setDatabase(options.getDatabase());
            if (options.getPassword() != null && !options.getPassword().isEmpty()) {
                serverConfig.setPassword(options.getPassword());
            }
        }
        return Redisson.create(config);
    }
}
