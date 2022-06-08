package com.ueueo.boot.autoconfigure.distributedlocking;

/**
 * Redis服务部署模式
 *
 * @author Lee
 * @date 2019-02-20 18:43
 */
public enum RedisServerType {
    /** 单机模式 */
    Single,
    /** 哨兵模式 */
    Sentinel
}
