package com.zjj.cache.component.repository.impl;

import com.zjj.cache.component.repository.RedisStreamRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBatch;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamReadArgs;
import org.redisson.api.stream.StreamReadGroupArgs;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 16:55
 */
@Component
@RequiredArgsConstructor
public class RedisStreamRepositoryImpl implements RedisStreamRepository {

	private final RedissonClient redissonClient;

	/**
	 * 添加事件到Stream。
	 * @param streamName Stream名称
	 * @param event Event内容
	 * @return 添加结果
	 */
	public <T> StreamMessageId addEvent(String streamName, T event) {
		RStream<String, T> stream = redissonClient.getStream(streamName);
		return stream.add(StreamAddArgs.entry("event", event));
	}


	/**
	 * 添加事件到Stream。
	 * @param streamName Stream名称
	 * @param event Event内容
	 * @return 添加结果
	 */
	public <K, V> StreamMessageId addEvent(String streamName, Map<K, V> event) {
		RStream<K, V> stream = redissonClient.getStream(streamName);
		return stream.add(StreamAddArgs.entries(event));
	}

	public long ack(String streamName, String groupName, StreamMessageId... ids) {
		RStream stream = redissonClient.getStream(streamName);
		return stream.ack(groupName, ids);
	}

	/**
	 * 创建消费者组。
	 * @param streamName Stream名称
	 * @param groupName 消费者组名称
	 */
	public void createGroup(String streamName, String groupName) {
		RStream stream = redissonClient.getStream(streamName);
		stream.createGroup(groupName);
	}

	public void createConsumer(String streamName, String groupName, String consumerName) {
		RStream stream = redissonClient.getStream(streamName);
		stream.createConsumer(groupName, consumerName);
	}

	public <V> void readEvent(String streamName, StreamMessageId id, Consumer<V> consumer) {
		RStream<String, V> stream = redissonClient.getStream(streamName);
		Map<StreamMessageId, Map<String, V>> read = stream.read(StreamReadArgs.greaterThan(id).count(10));
		consumer.accept(read.get(id).get("event"));
	}

	public <V> void readGroupEvent(String streamName, String groupName, StreamMessageId id, String consumerName, Consumer<V> consumer) {
		RStream<String, V> stream = redissonClient.getStream(streamName);
		Map<StreamMessageId, Map<String, V>> read = stream.readGroup(groupName, consumerName, StreamReadGroupArgs.greaterThan(id));
		consumer.accept(read.get(id).get("event"));
		stream.ack(groupName, id);
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
