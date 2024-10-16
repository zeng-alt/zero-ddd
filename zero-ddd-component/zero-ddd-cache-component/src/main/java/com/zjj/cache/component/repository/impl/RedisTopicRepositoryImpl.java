package com.zjj.cache.component.repository.impl;


import com.zjj.cache.component.repository.RedisSubPubRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.RedissonReliableTopic;
import org.redisson.api.*;
import org.redisson.api.listener.ListAddListener;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 16:36
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisTopicRepositoryImpl implements RedisSubPubRepository {

	private final RedissonClient redissonClient;

	/**
	 * 订阅频道并接收消息。
	 * @param channelKey 频道名
	 */
	public <M> String addListener(String channelKey, Class<M> clazz, MessageListener<M> listener) {

		RTopic name = redissonClient.getTopic("name");
//		name.addli

		RedissonReliableTopic topic = (RedissonReliableTopic) redissonClient.getReliableTopic(channelKey);
//		topic.
//		topic
		return topic.addListener(clazz, listener);
	}

	/**
	 * 发布消息到频道。
	 * @param channelKey 频道名
	 * @param message 消息内容
	 */
	public <T> long publish(String channelKey, T message) {
		RReliableTopic topic = redissonClient.getReliableTopic(channelKey);
		return topic.publish(message);
	}

	public void removeListener(String channelKey, int listenerId) {
		RReliableTopic reliableTopic = redissonClient.getReliableTopic(channelKey);
		reliableTopic.removeListener(listenerId);
	}

	public List<String> getChannelNames(String channelKey) {
		return redissonClient.getTopic(channelKey).getChannelNames();
	}

	public void removeAllListeners(String channelKey) {
		redissonClient.getReliableTopic(channelKey).removeAllListeners();
	}

	public long countSubscribers(String channelKey) {
		return redissonClient.getReliableTopic(channelKey).countSubscribers();
	}

	public int countListeners(String channelKey) {
		return redissonClient.getReliableTopic(channelKey).countListeners();
	}

	public <T> void removeListener(String channelKey, String listenerId) {
		RReliableTopic reliableTopic = redissonClient.getReliableTopic(channelKey);
		redissonClient.getReliableTopic(channelKey).removeListener(listenerId);
	}


}
