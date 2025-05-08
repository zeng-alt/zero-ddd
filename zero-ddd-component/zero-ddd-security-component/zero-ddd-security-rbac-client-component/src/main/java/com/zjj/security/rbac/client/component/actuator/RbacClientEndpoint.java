package com.zjj.security.rbac.client.component.actuator;

import com.zjj.security.rbac.client.component.RouteTemplateSupper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.util.Assert;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月08日 09:39
 */
@Slf4j
@Endpoint(id = "rbac")
public class RbacClientEndpoint {


    private final RouteTemplateSupper routeTemplateSupper;


    private RbacClientEndpoint(RouteTemplateSupper routeTemplateSupper) {

        log.debug("Activating Rbac client actuator.");

        this.routeTemplateSupper = routeTemplateSupper;
    }


    public static RbacClientEndpoint precomputed(RouteTemplateSupper routeTemplateSupper) {

        Assert.notNull(routeTemplateSupper, "routeTemplateSupper content must not be null!");

        return new RbacClientEndpoint(routeTemplateSupper);
    }


    @ReadOperation
    String sendRouterTemplate() {
        routeTemplateSupper.sendRouterTemplate();
        return "ok";
    }
}
