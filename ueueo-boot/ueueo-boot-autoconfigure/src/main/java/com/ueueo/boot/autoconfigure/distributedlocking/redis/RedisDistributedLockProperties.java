package com.ueueo.boot.autoconfigure.distributedlocking.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Reids地址配置
 *
 * @author Lee
 * @date 2018/11/26
 */
@Data
@ConfigurationProperties(prefix = RedisDistributedLockProperties.CONFIG_PREFIX)
public class RedisDistributedLockProperties {

    public static final String CONFIG_PREFIX = "ueueo.distributedlocking.redis";

    /** 是否启用，默认开启 */
    private boolean isEnable = true;
    /** Redis部署方式 */
    private RedisServerType serverType = RedisServerType.Single;
    /** 数据库 */
    private Integer database;
    /** 密码 */
    private String password;
    /** 单机模式下 主机地址 */
    private String address;
    /** 哨兵模式下 主节点名称 */
    private String masterName;
    /** 哨兵模式下 所有哨兵节点地址 */
    private String[] sentinelAddresses;

    /**
     * If pooled connection not used for a <code>timeout</code> time
     * and current connections amount bigger than minimum idle connections pool size,
     * then it will closed and removed from pool.
     * Value in milliseconds.
     */
    private int idleConnectionTimeout = 10000;

    /**
     * Timeout during connecting to any Redis server.
     * Value in milliseconds.
     */
    private int connectTimeout = 10000;

    /**
     * Redis server response timeout. Starts to countdown when Redis command was succesfully sent.
     * Value in milliseconds.
     */
    private int timeout = 3000;

    private int retryAttempts = 3;

    private int retryInterval = 1500;
}
