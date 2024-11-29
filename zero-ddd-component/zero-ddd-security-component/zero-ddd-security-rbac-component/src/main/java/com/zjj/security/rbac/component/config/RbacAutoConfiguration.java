package com.zjj.security.rbac.component.config;

import com.zjj.security.rbac.component.supper.DefaultHttpResourceService;
import com.zjj.security.rbac.component.supper.HttpResourceService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:40
 */
@AutoConfiguration
public class RbacAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpResourceService httpResourceService() {
        return new DefaultHttpResourceService();
    }
}
