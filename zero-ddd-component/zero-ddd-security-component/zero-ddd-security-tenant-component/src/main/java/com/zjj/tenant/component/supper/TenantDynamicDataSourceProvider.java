package com.zjj.tenant.component.supper;

import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.component.entity.ITenantEntity;
import com.zjj.tenant.component.spi.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月24日 20:47
 */
@RequiredArgsConstructor
public class TenantDynamicDataSourceProvider implements DynamicDataSourceProvider {


    private final DefaultDataSourceCreator dataSourceCreator;
    private final TenantRepository tenantRepository;

    @Override
    public Map<String, DataSource> loadDataSources() {
        List<ITenantEntity> list = tenantRepository.queryAll();

        HashMap<String, DataSource> map = new HashMap<>();

        for (ITenantEntity entity : list) {
            if (!entity.getEnabled()) {
                continue;
            }
            map.put(entity.getTenantKey(), dataSourceCreator.createDataSource(BeanHelper.copyToObject(entity, DataSourceProperty.class)));
        }
        return map;
    }
}
