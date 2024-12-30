package com.zjj.security.captcha.component.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月30日 16:21
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.captcha")
public class CaptchaProperties {

    private Boolean enable = true;

    private String captchaParameter = "captcha";

    private String captchaKeyParameter = "captchaKey";

    private List<String> filterUrl = List.of("/login/**");
    // 时间单位秒
    private Long expireTime = 60L;
}
