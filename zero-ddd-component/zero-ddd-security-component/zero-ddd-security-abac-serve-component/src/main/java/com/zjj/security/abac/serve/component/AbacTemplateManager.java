package com.zjj.security.abac.serve.component;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.autoconfigure.component.security.abac.AbacContextJsonEntity;
import com.zjj.autoconfigure.component.security.abac.AbacTemplateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 15:12
 */
@Slf4j
@RequiredArgsConstructor
public class AbacTemplateManager implements Consumer<AbacTemplateEvent>, InitializingBean {

    private final Map<String, AbacContextJsonEntity> abacEntity = new ConcurrentHashMap<>();
    private final RedisSubPubRepository redisSubPubRepository;

    public void addAbacTemplate(String key, AbacContextJsonEntity templates) {
        this.abacEntity.put(key, templates);
    }

    public AbacContextJsonEntity getAbacTemplate(String key) {
        return this.abacEntity.get(key);
    }

    @Override
    public void accept(AbacTemplateEvent routeTemplateEvent) {
        this.addAbacTemplate(routeTemplateEvent.getKey(), routeTemplateEvent.getAbacContextJsonEntity());
        log.info("{} abac模板更新成功", routeTemplateEvent.getAbacContextJsonEntity());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.redisSubPubRepository.addListener("abac:template", AbacTemplateEvent.class, this);
    }
}
