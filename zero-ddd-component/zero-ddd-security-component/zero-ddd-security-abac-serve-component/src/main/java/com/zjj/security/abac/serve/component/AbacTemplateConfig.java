package com.zjj.security.abac.serve.component;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.autoconfigure.component.security.SecurityProperties;
import com.zjj.autoconfigure.component.security.abac.AbacContextJsonEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年07月02日 15:58
 */
@Configuration
public class AbacTemplateConfig {

    @Bean
    public AbacTemplateManager abacTemplateManager(RedisSubPubRepository redisSubPubRepository) {
        return new AbacTemplateManager(redisSubPubRepository);
    }

    @Bean
    public RouterFunction<ServerResponse> abacRouterFunction(AbacTemplateManager abacModelHandler, SecurityProperties securityProperties) {
        return RouterFunctions.nest(RequestPredicates.path(securityProperties.getAbacPrefix() + "/abac"),
                RouterFunctions.route()
                        .GET("/{key}", request -> ServerResponse.ok().body(abacModelHandler.getAbacTemplate(request.pathVariable("key"))))
                        .POST("/{key}", request -> {
                            AbacContextJsonEntity body = request.body(AbacContextJsonEntity.class);
                            String key = request.pathVariable("key");
                            abacModelHandler.addAbacTemplate(key, body);
                            return ServerResponse.ok().build();
                        })
                        .build()
        );

    }
}
