package com.zjj.tenant.component.repository;

import com.zjj.tenant.component.entity.ITenantEntity;
import com.zjj.tenant.component.spi.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月28日 17:01
 */
@Component
@RequiredArgsConstructor
public class TenantRepositoryImpl implements TenantRepository {

    private final DefaultTenantRepository defaultTenantRepository;


    @Override
    public List<ITenantEntity> queryAll() {
        return defaultTenantRepository.findAll();
    }

    @Override
    public Optional<ITenantEntity> findByTenantKey(String tenantKey) {
        return Optional.empty();
    }
}
