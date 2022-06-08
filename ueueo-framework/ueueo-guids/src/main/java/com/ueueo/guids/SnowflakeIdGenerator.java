package com.ueueo.guids;

import com.ueueo.ID;
import com.ueueo.guids.algorithm.SnowflakeIdWorker;

/**
 * Snowflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。生成的是一个64位的二进制正整数，
 * 然后转换成10进制的数。
 *
 * 其核心思想是：使用41bit作为毫秒数，10bit作为机器的ID（5个bit是数据中心ID(dataCenterId)，5个bit的机器ID(workerId)），
 * 12bit作为毫秒内的流水号（意味着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0。
 *
 * 优点:
 * 1）简单高效，生成速度快。
 * 2）时间戳在高位，自增序列在低位，整个ID是趋势递增的，按照时间有序递增。
 * 3）灵活度高，可以根据业务需求，调整bit位的划分，满足不同的需求。
 *
 * 缺点:
 * 1）依赖机器的时钟，如果服务器时钟回拨，会导致重复ID生成。
 * 2）在分布式环境上，每个服务器的时钟不可能完全同步，有时会出现不是全局递增的情况。
 *
 * @author Lee
 * @date 2022-03-14 14:30
 */
public class SnowflakeIdGenerator implements IGuidGenerator {

    private final SnowflakeIdWorker snowflakeIdWorker;

    /**
     * Snowflake构造方案
     *
     * @param workerId     机器ID（0~31）
     * @param dataCenterId 数据中心ID（0~31）
     */
    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        this.snowflakeIdWorker = new SnowflakeIdWorker(workerId, dataCenterId);
    }

    @Override
    public ID create() {
        return ID.valueOf(snowflakeIdWorker.nextId());
    }

}
