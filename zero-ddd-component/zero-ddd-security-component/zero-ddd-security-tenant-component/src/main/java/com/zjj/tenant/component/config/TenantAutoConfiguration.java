package com.zjj.tenant.component.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.zjj.tenant.component.spi.DynamicSourceManage;
import com.zjj.tenant.component.spi.TenantContextHolder;
import com.zjj.tenant.component.supper.DefaultDynamicSourceManage;
import com.zjj.tenant.component.supper.DefaultTenantContextHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月28日 10:30
 */
@AutoConfiguration(after = DynamicDataSourceAutoConfiguration.class)
public class TenantAutoConfiguration {

//    @Bean
//    public DynamicDataSourceProvider tenantDynamicDataSourceProvider(DefaultDataSourceCreator dataSourceCreator, TenantRepository tenantRepository) {
//        return new TenantDynamicDataSourceProvider(dataSourceCreator, tenantRepository);
//    }


    @Bean
    public DynamicSourceManage dynamicSourceManage(DataSource dataSource, DynamicDataSourceProperties dynamicDataSourceProperties) {

        return new DefaultDynamicSourceManage((DynamicRoutingDataSource) dataSource, dynamicDataSourceProperties);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TenantContextHolder defaultTenantContextHolder(DynamicSourceManage dynamicSourceManage) {
        return new DefaultTenantContextHolder(dynamicSourceManage);
    }
}
