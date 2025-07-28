package com.zjj.security.abac.component.object;


import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.autoconfigure.component.security.abac.AbacContextJsonEntity;
import com.zjj.autoconfigure.component.security.abac.AbacTemplateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月10日 16:05
 */
@Slf4j
@RequiredArgsConstructor
public class AbacJsonMappingHandlerMapping implements AbacMappingHandlerMapping<AbacContextJsonEntity> {

    private final RedisSubPubRepository redisSubPubRepository;

    private final Map<String, AbacContextJsonEntity> nameLookup = new ConcurrentHashMap<>();


    public void register(String name, AbacContextJsonEntity abacContextEntity) {
        this.nameLookup.put(name, abacContextEntity);
    }

    public void send() {
        for (Map.Entry<String, AbacContextJsonEntity> entry : nameLookup.entrySet()) {
            AbacTemplateEvent abacTemplateEvent = new AbacTemplateEvent();
            abacTemplateEvent.setKey(entry.getKey());
            abacTemplateEvent.setAbacContextJsonEntity(entry.getValue());
            redisSubPubRepository.publish("abac:template", abacTemplateEvent);
        }

        log.info("发送路由模板成功!!!!");
    }
}
