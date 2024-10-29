package com.zjj.tenant.component.supper;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.l2.cache.component.provider.L2BuilderProvider;
import com.zjj.l2.cache.component.supper.RedissonCaffeineCache;
import com.zjj.memory.component.provider.Tuple;
import com.zjj.tenant.component.entity.ITenantEntity;
import com.zjj.tenant.component.spi.TenantContextHolder;
import com.zjj.tenant.component.spi.TenantRepository;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:14
 */
@RequiredArgsConstructor
public class TenantL2CacheProvider implements L2BuilderProvider {

    private final DynamicRoutingDataSource dataSource;
    private final TenantRepository tenantRepository;
    private final TenantContextHolder tenantContextHolder;
    private final DataSourceCreator dataSourceCreator;

    @Override
    public Tuple<RedissonCaffeineCache.Builder> consumerBuilder() {
        RedissonCaffeineCache.Builder builder = RedissonCaffeineCache.builder();
        builder.name("tenant")
                .cacheNullValue(false)
                .l1(consumer -> consumer
                        .maximumSize(1000)
                        .initialCapacity(100)
                        .removalListener( ( key, value, cause ) -> {
                            if (!cause.wasEvicted()) {
                                dataSource.removeDataSource((String) key);
                            }
                        })
                        .evictionListener((key, value, cause) -> {
                            if (RemovalCause.REPLACED.equals(cause)) {
                                dataSource.addDataSource((String) key, dataSourceCreator.createDataSource(BeanHelper.copyToObject(value, DataSourceProperty.class)));
                            }
                        }))
                .l2(consumer -> consumer.expire(Duration.ZERO));
        return Tuple.of("tenant", builder);
    }
}
