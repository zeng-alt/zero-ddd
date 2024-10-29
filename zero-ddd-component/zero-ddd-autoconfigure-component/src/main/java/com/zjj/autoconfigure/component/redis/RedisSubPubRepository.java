package com.zjj.autoconfigure.component.redis;

import org.redisson.api.listener.MessageListener;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 16:35
 */
public interface RedisSubPubRepository {
    /**
     * 订阅频道并接收消息。
     * @param channelKey 频道名
     */
    public <T> String addListener(String channelKey, Class<T> clazz, Consumer<T> consumer);

    /**
     * 发布消息到频道。
     * @param channelKey 频道名
     * @param message 消息内容
     */
    public <T> long publish(String channelKey, T message);

    public void removeListener(String channelKey, int listenerId);

    public List<String> getChannelNames(String channelKey);

    public void removeAllListeners(String channelKey);

    public long countSubscribers(String channelKey);

    public int countListeners(String channelKey);
}
