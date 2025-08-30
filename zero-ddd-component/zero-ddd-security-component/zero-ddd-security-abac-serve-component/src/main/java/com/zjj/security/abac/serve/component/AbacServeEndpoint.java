package com.zjj.security.abac.serve.component;

import com.zjj.autoconfigure.component.security.abac.AbacContextJsonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年08月04日 14:44
 */
@Slf4j
@Endpoint(id = "abac.serve")
public class AbacServeEndpoint {

    private final AbacTemplateManager abacTemplateManager;

    public AbacServeEndpoint(AbacTemplateManager abacTemplateManager) {
        log.debug("Activating ABAC serve actuator.");
        this.abacTemplateManager = abacTemplateManager;
    }

    @ReadOperation
    public AbacContextJsonEntity getInfo(@Selector String key) {
        return abacTemplateManager.getAbacTemplate(key);
    }
}
