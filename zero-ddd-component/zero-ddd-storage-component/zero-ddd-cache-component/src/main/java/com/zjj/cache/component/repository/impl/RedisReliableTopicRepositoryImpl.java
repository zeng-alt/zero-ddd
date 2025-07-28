package com.zjj.cache.component.repository.impl;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RReliableTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 20:36
 */
@Component
public class RedisReliableTopicRepositoryImpl implements RedisSubPubRepository {

    private final RedissonClient redissonClient;

    @Autowired
    public RedisReliableTopicRepositoryImpl(RedissonClient template) {
        this.redissonClient = template;
    }

    @Override
    public <T> String addListener(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RReliableTopic topic = redissonClient.getReliableTopic(channelKey);
        return topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    @Override
    public <T> long publish(String channelKey, T message) {
        RReliableTopic topic = redissonClient.getReliableTopic(channelKey);
        return topic.publish(message);
    }

    @Override
    public void removeListener(String channelKey, int listenerId) {
        RReliableTopic topic = redissonClient.getReliableTopic(channelKey);
        topic.removeListener(listenerId);
    }

    @Override
    public List<String> getChannelNames(String channelKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAllListeners(String channelKey) {
        RReliableTopic topic = redissonClient.getReliableTopic(channelKey);
        topic.removeAllListeners();
    }

    @Override
    public long countSubscribers(String channelKey) {
        RReliableTopic topic = redissonClient.getReliableTopic(channelKey);
        return topic.countSubscribers();
    }

    @Override
    public int countListeners(String channelKey) {
        RReliableTopic topic = redissonClient.getReliableTopic(channelKey);
        return topic.countListeners();
    }
}
