package com.zjj.security.sms.component.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月01日 22:32
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.sms")
public class SmsLoginProperties {

    private Boolean enable = true;
    private String mobileParameter = "mobile";
    private String codeParameter = "code";
    private String url = "/login/sms";
}
