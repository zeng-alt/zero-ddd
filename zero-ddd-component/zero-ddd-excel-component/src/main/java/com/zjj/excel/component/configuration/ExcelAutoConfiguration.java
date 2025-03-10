package com.zjj.excel.component.configuration;

import cn.idev.excel.metadata.GlobalConfiguration;
import com.zjj.excel.component.utils.ExcelHelper;
import com.zjj.i18n.component.config.MessageBaseNameProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
    @ConfigurationProperties(prefix = "fast.excel")
    public GlobalConfiguration globalConfiguration() {
        return new GlobalConfiguration();
    }

    @Bean
    @ConditionalOnMissingBean(ExcelTemplate.class)
    public ExcelTemplate excelTemplate(GlobalConfiguration globalConfiguration) {
        return new DefaultExcelTemplate(globalConfiguration);
    }

    @Bean
    public ExcelHelper excelHelper() {
        return new ExcelHelper();
    }


    @Bean
    public MessageBaseNameProvider excelMessageBaseNameProvider() {
        return () -> new String[] {"excel"};
    }
}
