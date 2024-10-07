package com.zjj.security.sms.component.configuration;


import com.zjj.autoconfigure.component.security.AbstractLoginConfigurer;
import com.zjj.security.sms.component.CodeService;
import com.zjj.security.sms.component.SmsDetailsService;
import com.zjj.security.sms.component.supper.DefaultSmsDetailsService;
import com.zjj.security.sms.component.supper.SmsAuthenticationFilter;
import com.zjj.security.sms.component.supper.SmsAuthenticationProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月06日 22:50
 * @version 1.0
 */
@Configuration
@AutoConfiguration
@EnableConfigurationProperties({SmsLoginProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(name = "security.sms.enabled", havingValue = "true", matchIfMissing = true)
public class SmsAutoConfiguration {

    @Bean
    public AbstractLoginConfigurer smsLoginConfigurer(SmsLoginProperties smsLoginProperties) {
        return new DefaultSmsLoginConfigurer(smsLoginProperties, new SmsAuthenticationFilter());
    }

    @Bean
    @ConditionalOnMissingBean
    public CodeService codeService() {
        return new CodeService() {
            @Override
            public String getCode(CharSequence mobile) {
                return null;
            }

            @Override
            public boolean matches(CharSequence mobile, String code) {
                return true;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public SmsDetailsService smsDetailsService() {
        return new DefaultSmsDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean
    public SmsAuthenticationProvider smsAuthenticationProvider(CodeService codeService, SmsDetailsService smsDetailsService) {
        return new SmsAuthenticationProvider(codeService, smsDetailsService);
    }

}
