package com.zjj.tenant.component.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.component.entity.ITenantEntity;
import com.zjj.tenant.component.spi.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DefaultDataSourceCreator dataSourceCreator;
    private final TenantRepository tenantRepository;
    private final DataSource dataSource;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<ITenantEntity> list = tenantRepository.queryAll();

        for (ITenantEntity entity : list) {
            if (!entity.getEnabled()) {
                continue;
            }
            if (dataSource instanceof DynamicRoutingDataSource dynamicRoutingDataSource) {
                dynamicRoutingDataSource.addDataSource(entity.getTenantKey(), dataSourceCreator.createDataSource(BeanHelper.copyToObject(entity, DataSourceProperty.class)));
            }
        }
    }
}
