package com.zjj.tenant.management.component.repository;

import com.zjj.tenant.management.component.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:17
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {
}
