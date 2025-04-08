package com.zjj.main.config;

import com.zjj.security.rbac.client.component.EndpointPrefix;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 16:55
 */
@Component
public class MainEndpointPrefix implements EndpointPrefix {

    @Override
    public String getPrefix() {
        return "/main";
    }
}
