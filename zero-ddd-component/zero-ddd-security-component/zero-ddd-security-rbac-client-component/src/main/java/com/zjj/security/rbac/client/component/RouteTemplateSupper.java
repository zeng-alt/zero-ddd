package com.zjj.security.rbac.client.component;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.autoconfigure.component.security.rbac.router.RouteTemplateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 15:58
 */
@Slf4j
@RequiredArgsConstructor
public class RouteTemplateSupper implements ApplicationContextAware, InitializingBean {

    private final EndpointPrefix endpointPrefix;
    private final RedisSubPubRepository redisSubPubRepository;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void sendRouterTemplate() {
        List<String> routePath = applicationContext
                .getBeanProvider(RequestMappingHandlerMapping.class)
                .stream()
                .flatMap(m -> m.getHandlerMethods().entrySet().stream())
                .map(e -> e.getKey().getPatternValues()).flatMap(Set::stream)
                .filter(s -> s.startsWith(endpointPrefix.getPrefix()))
                .toList();
        RouteTemplateEvent routeTemplateEvent = new RouteTemplateEvent();
        routeTemplateEvent.setContextPath(endpointPrefix.getPrefix());
        routeTemplateEvent.setTemplates(routePath);
        redisSubPubRepository.publish("route:template:path", routeTemplateEvent);
        log.info("发送路由模板成功!!!!");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this::sendRouterTemplate).start();
    }
}
