package com.ueueo.boot.autoconfigure.distributedlocking;

import com.ueueo.boot.autoconfigure.distributedlocking.redis.RedisDistributedLockProperties;
import com.ueueo.boot.autoconfigure.distributedlocking.redis.RedisServerType;
import com.ueueo.distributedlocking.IDistributedLock;
import com.ueueo.distributedlocking.LocalDistributedLock;
import com.ueueo.distributedlocking.redis.RedisDistributedLock;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.BaseConfig;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Lee
 * @date 2022-05-27 17:57
 */
@Configuration
public class DistributedLockingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IDistributedLock.class)
    public IDistributedLock distributedLock() {
        return new LocalDistributedLock();
    }

    @ConditionalOnProperty(value = RedisDistributedLockProperties.CONFIG_PREFIX + ".enable", havingValue = "true", matchIfMissing = true)
    @ConditionalOnClass({RedissonClient.class, RedisDistributedLock.class})
    @EnableConfigurationProperties(RedisDistributedLockProperties.class)
    static class RedisConfiguration {

        @Bean
        @ConditionalOnMissingBean(RedissonClient.class)
        public RedissonClient redissonClient(RedisDistributedLockProperties options) {
            Config config = new Config();
            BaseConfig<?> serverConfig;
            if (RedisServerType.Sentinel.equals(options.getServerType())) {
                serverConfig = config.useSentinelServers()
                        .addSentinelAddress(options.getSentinelAddresses())
                        .setMasterName(options.getMasterName())
                        .setDatabase(options.getDatabase());
            } else {
                serverConfig = config.useSingleServer()
                        .setAddress(options.getAddress())
                        .setDatabase(options.getDatabase());
            }
            if (options.getPassword() != null && !options.getPassword().isEmpty()) {
                serverConfig.setPassword(options.getPassword());
            }
            serverConfig.setIdleConnectionTimeout(options.getIdleConnectionTimeout());
            serverConfig.setConnectTimeout(options.getConnectTimeout());
            serverConfig.setTimeout(options.getTimeout());
            serverConfig.setRetryAttempts(options.getRetryAttempts());
            serverConfig.setRetryInterval(options.getRetryInterval());
            return Redisson.create(config);
        }

        @Bean
        @Primary
        public IDistributedLock distributedLock(RedissonClient redissonClient) {
            return new RedisDistributedLock(redissonClient);
        }
    }

}
