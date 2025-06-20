package com.zjj.tenant.infrastructure.repository;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.core.component.crypto.EncryptionService;
import com.zjj.domain.component.DomainBeanHelper;
import com.zjj.security.abac.component.annotation.ObjectMethod;
import com.zjj.tenant.domain.tenant.TenantDataSource;
import com.zjj.tenant.domain.tenant.TenantDataSourceRepository;
import com.zjj.tenant.infrastructure.db.jpa.TenantDataSourceDao;
import com.zjj.tenant.infrastructure.exception.DataSourceConnectionException;
import io.vavr.control.Option;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:50
 */
@Component
public record TenantDataSourceRepositoryImpl(TenantDataSourceDao tenantDataSourceDao, EncryptionService encryptionService) implements TenantDataSourceRepository {


    @Override
    public Option<TenantDataSource> findBySchema(String schema) {
        return tenantDataSourceDao.findBySchema(schema).map(t -> DomainBeanHelper.copyToDomain(t, TenantDataSource.class, TenantDataSource.TenantDataSourceId.class));
    }

    @Override
    public Option<TenantDataSource> findByDb(String db) {
        return tenantDataSourceDao.findByDb(db).map(t -> DomainBeanHelper.copyToDomain(t, TenantDataSource.class, TenantDataSource.TenantDataSourceId.class));
    }

    @Override
    public Option<TenantDataSource> findById(Long id) {
//        return tenantDataSourceDao.findById(id).map(t -> DomainBeanHelper.copyToDomain(t, TenantDataSource.class, TenantDataSource.TenantDataSourceId.class));
        return tenantDataSourceDao
                .findById(id)
                .map(t -> BeanHelper.copyToObject(t, TenantDataSource.class));
    }

    @Override
    @ObjectMethod(value = "dataSource")
    public TenantDataSource findTenantDataSourceById(Long id) {
        return findById(id).getOrNull();
    }

    @Override
    public void testDataSourceConnectionCmd(Long id) {
        tenantDataSourceDao
                .findById(id)
                .map(t -> {
                    String url = "jdbc:mysql://localhost:3306/your_schema?useSSL=false&serverTimezone=UTC";
                    String username = "your_username";
                    String password = "your_password";

                    try (Connection conn = DriverManager.getConnection(url, username, password)) {
                    } catch (Exception e) {
                        throw new DataSourceConnectionException(e.getMessage());
                    }
                    return t;
                })
                .getOrElseThrow(() -> new IllegalArgumentException(id + " 数据源不存在, 无法测试"));
    }
}
