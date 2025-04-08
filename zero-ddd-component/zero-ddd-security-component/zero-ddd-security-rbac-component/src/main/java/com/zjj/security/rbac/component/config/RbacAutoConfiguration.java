package com.zjj.security.rbac.component.config;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.security.rbac.component.router.RouteTemplateManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:40
 */
@AutoConfiguration
public class RbacAutoConfiguration {

    @Bean
    public RouteTemplateManager routeTemplateManager(RedisSubPubRepository redisSubPubRepository) {
        return new RouteTemplateManager(redisSubPubRepository);
    }
}
