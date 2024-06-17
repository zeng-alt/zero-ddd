package com.zjj.doc.config.properties;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 22:15
 * @version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "openapi.info")
public class InfoProperties {
    /**
     * 标题
     */
    private String title = null;

    /**
     * 描述
     */
    private String description = null;

    /**
     * 联系人信息
     */
    @NestedConfigurationProperty
    private Contact contact = null;

    /**
     * 许可证
     */
    @NestedConfigurationProperty
    private License license = null;

    /**
     * 版本
     */
    private String version = null;

}
