package com.zjj.autoconfigure.component.redis;

import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamReadArgs;
import org.redisson.api.stream.StreamReadGroupArgs;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 19:54
 */
public interface RedisStreamRepository {

    public <T> StreamMessageId addEvent(String streamName, T event);

    /**
     * 添加事件到Stream。
     * @param streamName Stream名称
     * @param event Event内容
     * @return 添加结果
     */
    public <K, V> StreamMessageId addEvent(String streamName, Map<K, V> event);

    public long ack(String streamName, String groupName, StreamMessageId... ids);

    /**
     * 创建消费者组。
     * @param streamName Stream名称
     * @param groupName 消费者组名称
     */
    public void createGroup(String streamName, String groupName);

    public void createConsumer(String streamName, String groupName, String consumerName);

    public <V> void readEvent(String streamName, StreamMessageId id, Consumer<V> consumer);

    public <V> void readGroupEvent(String streamName, String groupName, StreamMessageId id, String consumerName, Consumer<V> consumer);

}
