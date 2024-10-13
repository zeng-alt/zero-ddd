package com.zjj.cache.component.repository.impl;

import com.zjj.cache.component.repository.RedisStreamRepository;
import org.redisson.PubSubMessageListener;
import org.redisson.api.*;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamReadArgs;
import org.redisson.api.stream.StreamReadGroupArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 16:55
 */
@Component
public class RedisStreamRepositoryImpl<K, V> implements RedisStreamRepository {

	private final RedissonClient redissonClient;

	@Autowired
	public RedisStreamRepositoryImpl(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
		RTopic topic = redissonClient.getTopic("test1");
//		topic.addListener(EvictionMode.class, new PubSubMessageListener<>() {
//
//		})
		RStream<Object, Object> test = redissonClient.getStream("test");
//		Map<StreamMessageId, Map<Object, Object>> read = test.read(StreamReadArgs.greaterThan().count().timeout());
	}

	/**
	 * 添加事件到Stream。
	 * @param streamName Stream名称
	 * @param event Event内容
	 * @return 添加结果
	 */
	public String addEvent(String streamName, Map<K, V> event) {
		RStream<K, V> stream = redissonClient.getStream(streamName);
		return stream.add(StreamAddArgs.entries(event)).toString();
	}

	/**
	 * 读取Stream中的事件。
	 * @param streamName Stream名称
	 * @param count 读取的事件数量
	 * @return 事件列表
	 */
	public Map<StreamMessageId, Map<K, V>> readEvents(String streamName, StreamMessageId id, int count,
			Duration timeout) {
		RStream<K, V> stream = redissonClient.getStream(streamName);
		return stream.read(StreamReadArgs.greaterThan(id).count(count).timeout(timeout));
	}

	/**
	 * 确认已处理的事件。
	 * @param streamName Stream名称
	 * @param ids 已处理的事件ID列表
	 * @return 确认结果
	 */
	// public RStreamWriteResult acknowledgeEvents(String streamName, List<String> ids) {
	// RStream<String, Object> stream = redissonClient.getStream(streamName);
	// return stream.ack(ids);
	// }

	/**
	 * 创建消费者组。
	 * @param groupName 消费者组名称
	 * @param streamName Stream名称
	 */
	public void createConsumerGroup(String groupName, String streamName) {
		RStream<String, Object> stream = redissonClient.getStream(streamName);
		stream.createGroup(groupName);
	}

	/**
	 * 设置消费者组的消费位置。
	 * @param groupName 消费者组名称
	 * @param streamName Stream名称
	 * @param id 消费位置ID
	 */
	// public void setConsumerPosition(String groupName, String streamName, String id) {
	// RStream<String, Object> stream = redissonClient.getStream(streamName);
	// stream.setGroupPosition(groupName, id);
	// }

	/**
	 * 订阅Stream。
	 * @param groupId 组ID
	 * @param groupStreamName Stream名称
	 * @param consumerName 消费者名称
	 * @param noAck 是否需要确认
	 * @return 订阅结果
	 */
	// public RStreamReadGroupResult subscribeStream(String groupId, String
	// groupStreamName, String consumerName, boolean noAck) {
	// RStream<String, Object> stream = redissonClient.getStream(groupStreamName);
	// return stream.readGroup(groupId, new
	// RStreamReadGroupOptions().consumer(consumerName).noAck(noAck));
	// }

	/**
	 * 从Stream中删除事件。
	 * @param streamName Stream名称
	 * @param ids 事件ID列表
	 * @return 删除结果
	 */
	// public RStreamWriteResult deleteEvents(String streamName, List<String> ids) {
	// RStream<String, Object> stream = redissonClient.getStream(streamName);
	// return stream.remove(ids);
	// }

}
