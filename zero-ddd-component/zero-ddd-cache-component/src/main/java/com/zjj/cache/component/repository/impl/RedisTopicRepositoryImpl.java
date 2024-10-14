package com.zjj.cache.component.repository.impl;


import com.zjj.cache.component.repository.RedisSubPubRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.*;
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
	public <T> int addListener(String channelKey, Class<T> clazz, Consumer<T> consumer) {
		RTopic topic = redissonClient.getTopic(channelKey);
//		redissonClient.getReliableTopic().ad
		return topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
	}

	/**
	 * 发布消息到频道。
	 * @param channelKey 频道名
	 * @param message 消息内容
	 */
	public <T> long publish(String channelKey, T message) {
		RTopic topic = redissonClient.getTopic(channelKey);
		return topic.publish(message);
	}

	public void removeListener(String channelKey, int listenerId) {
		redissonClient.getTopic(channelKey).removeListener(listenerId);
	}

	public List<String> getChannelNames(String channelKey) {
		return redissonClient.getTopic(channelKey).getChannelNames();
	}

	public void removeAllListeners(String channelKey) {
		redissonClient.getTopic(channelKey).removeAllListeners();
	}

	public long countSubscribers(String channelKey) {
		return redissonClient.getTopic(channelKey).countSubscribers();
	}

	public int countListeners(String channelKey) {
		return redissonClient.getTopic(channelKey).countListeners();
	}

	public <T> void removeListener(String channelKey, MessageListener<T> listener) {
		redissonClient.getTopic(channelKey).removeListener(listener);
	}

}
