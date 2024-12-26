package com.zjj.exchange.common;

import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import feign.RequestInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 21:47
 */
@AutoConfiguration
@EnableConfigurationProperties({MultiTenancyProperties.class, JwtProperties.class})
public class FeignInterceptorAutoConfig {


    @Bean
    public RequestInterceptor feignClientInterceptor(MultiTenancyProperties multiTenancyProperties, JwtProperties jwtProperties) {
        return new FeignClientInterceptor(multiTenancyProperties.getTenantToken(), jwtProperties.getFastToken());
    }

//    @Value("${multi-tenancy.tenant-token:X-TENANT-ID}")
//    private String tenantToken;
//    @Value("${security.jwt.ast-token:User}")
//    private String fastToken;

//    @Bean
//    public RequestInterceptor feignClientInterceptor(ObjectProvider<MultiTenancyProperties> multiTenancyProperties, ObjectProvider<JwtProperties> jwtProperties) {
//        MultiTenancyProperties properties = multiTenancyProperties.getIfAvailable();
//        JwtProperties jwtPropertie = jwtProperties.getIfAvailable();
//        return new FeignClientInterceptor(properties != null ? properties.getTenantToken() : tenantToken, jwtPropertie != null ? jwtPropertie.getFastToken() : fastToken);
//    }


//    @Bean
//    public Decoder feignClientDecorator(ObjectFactory<HttpMessageConverters> messageConverters, ObjectProvider<HttpMessageConverterCustomizer> customizers, ObjectProvider<MultiTenancyProperties> multiTenancyProperties) {
//        MultiTenancyProperties properties = multiTenancyProperties.getIfAvailable();
//        OptionalDecoder decoder = new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters, customizers)));
//        return new FeignClientDecorator(decoder, properties != null ? properties.getTenantToken() : tenantToken);
//    }
}
