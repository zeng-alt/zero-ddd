package com.zjj.security.abac.component.actuator;

import com.zjj.security.abac.component.object.AbacMappingHandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年08月04日 21:54
 */
@Slf4j
@Endpoint(id = "abac.client")
public class AbacClientEndpoint {
    private final AbacMappingHandlerMapping abacMappingHandlerMapping;

    public AbacClientEndpoint(AbacMappingHandlerMapping abacMappingHandlerMapping) {
        log.debug("AbacClientEndpoint init");
        this.abacMappingHandlerMapping = abacMappingHandlerMapping;
    }

    @ReadOperation
    public void send() {
        abacMappingHandlerMapping.send();
    }
}
