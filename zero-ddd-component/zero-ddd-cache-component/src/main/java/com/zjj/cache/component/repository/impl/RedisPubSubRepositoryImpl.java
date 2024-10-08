package com.zjj.cache.component.repository.impl;

import com.zjj.cache.component.domain.Message;
import com.zjj.cache.component.repository.RedisPubSubRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 16:36
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisPubSubRepositoryImpl implements RedisPubSubRepository {

	private final RedissonClient redissonClient;

	/**
	 * 订阅频道并接收消息。
	 * @param channelKey 频道名
	 */
	public void subscribe(String channelKey, Class<Message> clazz, Consumer<Message> consumer) {
		RTopic topic = redissonClient.getTopic(channelKey);
		topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
	}

	/**
	 * 发布消息到频道。
	 * @param channelKey 频道名
	 * @param message 消息内容
	 */
	public void publish(String channelKey, Message message, Consumer<Message> consumer) {
		RTopic topic = redissonClient.getTopic(channelKey);
		topic.publish(message);
		consumer.accept(message);
	}

	/**
	 * 发布消息到频道。
	 * @param channelKey 频道名
	 * @param message 消息内容
	 */
	public void publish(String channelKey, Message message) {
		RTopic topic = redissonClient.getTopic(channelKey);
		topic.publish(message);
	}

}
