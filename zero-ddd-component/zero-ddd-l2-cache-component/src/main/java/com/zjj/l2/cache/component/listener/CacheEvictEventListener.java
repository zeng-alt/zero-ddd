package com.zjj.l2.cache.component.listener;

import com.zjj.l2.cache.component.event.EvictEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月12日 20:55
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class CacheEvictEventListener implements MessageListener<EvictEvent> {

    @Override
    public void onMessage(CharSequence channel, EvictEvent event) {

    }
}
