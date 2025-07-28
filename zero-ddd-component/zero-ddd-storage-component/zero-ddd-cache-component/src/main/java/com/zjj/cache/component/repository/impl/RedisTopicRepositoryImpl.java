package com.zjj.cache.component.repository.impl;


import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 16:36
 */
@Primary
@Component
public class RedisTopicRepositoryImpl implements RedisSubPubRepository {

	private final RedissonClient redissonClient;

	@Autowired
	public RedisTopicRepositoryImpl(RedissonClient template) {
		this.redissonClient = template;
	}

	/**
	 * 订阅频道并接收消息。
	 * @param channelKey 频道名
	 */
	@Override
	public <T> String addListener(String channelKey, Class<T> clazz, Consumer<T> consumer) {
		RTopic topic = redissonClient.getTopic(channelKey);
		return String.valueOf(topic.addListener(clazz, (channel, msg) -> consumer.accept(msg)));
	}

	/**
	 * 发布消息到频道。
	 * @param channelKey 频道名
	 * @param message 消息内容
	 */
	@Override
	public <T> long publish(String channelKey, T message) {
		RTopic topic = redissonClient.getTopic(channelKey);
		return topic.publish(message);
	}

	@Override
	public void removeListener(String channelKey, int listenerId) {
		redissonClient.getTopic(channelKey).removeListener(listenerId);
	}

	@Override
	public List<String> getChannelNames(String channelKey) {
		return redissonClient.getTopic(channelKey).getChannelNames();
	}

	@Override
	public void removeAllListeners(String channelKey) {
		redissonClient.getTopic(channelKey).removeAllListeners();
	}

	@Override
	public long countSubscribers(String channelKey) {
		return redissonClient.getTopic(channelKey).countSubscribers();
	}

	@Override
	public int countListeners(String channelKey) {
		return redissonClient.getTopic(channelKey).countListeners();
	}

	public <T> void removeListener(String channelKey, MessageListener<T> listener) {
		redissonClient.getTopic(channelKey).removeListener(listener);
	}

}
