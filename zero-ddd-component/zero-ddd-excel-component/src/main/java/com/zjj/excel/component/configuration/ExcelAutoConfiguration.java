package com.zjj.excel.component.configuration;

import cn.idev.excel.metadata.GlobalConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 14:44
 */
@AutoConfiguration
@EnableConfigurationProperties(GlobalConfiguration.class)
public class ExcelAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "excel")
    public GlobalConfiguration globalConfiguration() {
        return new GlobalConfiguration();
    }
}
