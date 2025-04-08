package com.zjj.security.rbac.component.router;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.autoconfigure.component.security.rbac.router.RouteTemplateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 15:12
 */
@Slf4j
@RequiredArgsConstructor
public class RouteTemplateManager implements Consumer<RouteTemplateEvent>, InitializingBean {

    private final RouteTemplateTrie trie = new RouteTemplateTrie();
    private final RedisSubPubRepository redisSubPubRepository;

    public void addRouteTemplate(String contextPath, List<String> templates) {
        trie.deleteSubtree(contextPath);
        for (String template : templates) {
            trie.insert(template);
        }
    }

    public String match(String path) {
        String match = trie.match(path);
        return StringUtils.hasText(match) ? match : path;
    }

    @Override
    public void accept(RouteTemplateEvent routeTemplateEvent) {
        this.addRouteTemplate(routeTemplateEvent.getContextPath(), routeTemplateEvent.getTemplates());
        log.info("{} 路由模板更新成功", routeTemplateEvent.getContextPath());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.redisSubPubRepository.addListener("route:template:path", RouteTemplateEvent.class, this);
    }
}
