package com.zjj.security.fast.auth.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.fast.auth.component.filter.FastAuthenticationFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:27
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class FastAuthAutoConfiguration {

    @Bean
    public FastAuthenticationFilter fastAuthenticationFilter(JwtCacheManage jwtCacheManage, JwtProperties jwtProperties) {
        return new FastAuthenticationFilter(jwtCacheManage, jwtProperties);
    }


    @Bean
    public SecurityBuilderCustomizer fastAuthCustomizer(FastAuthenticationFilter fastAuthenticationFilter) {
        return http -> http.addFilterBefore(fastAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
