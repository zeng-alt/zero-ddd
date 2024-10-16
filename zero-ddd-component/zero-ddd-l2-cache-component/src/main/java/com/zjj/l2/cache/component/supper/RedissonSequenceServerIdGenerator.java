package com.zjj.l2.cache.component.supper;

import com.zjj.autoconfigure.component.l2cache.SequenceServerIdGenerator;
import com.zjj.cache.component.repository.RedisStringRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月16日 14:15
 */
@RequiredArgsConstructor
public class RedissonSequenceServerIdGenerator implements SequenceServerIdGenerator {

    private final String key;
    private final RedisStringRepository redisStringRepository;

    @Override
    public Object nextId() {
        return redisStringRepository.increment(key);
    }
}
