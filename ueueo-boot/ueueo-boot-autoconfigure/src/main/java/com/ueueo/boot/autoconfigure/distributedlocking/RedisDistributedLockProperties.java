package com.ueueo.boot.autoconfigure.distributedlocking;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Reids地址配置
 *
 * @author Lee
 * @date 2018/11/26
 */
@Data
@ConfigurationProperties(prefix = "ueueo.distributedlock.redis")
public class RedisDistributedLockProperties {
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

}
