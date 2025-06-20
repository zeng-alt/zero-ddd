package com.zjj.tenant.management.component.service;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.autoconfigure.component.redis.Lock;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.core.component.crypto.EncryptionService;
import liquibase.exception.LiquibaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月22日 16:30
 * @version 1.0
 */
@Slf4j
public class TenantManagementServiceImpl implements TenantManagementService, InitializingBean {


    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;

    private final TenantDataSourceService tenantDataSourceService;
//    private final TenantInitDataSourceService tenantInitDataSourceService;
    private Lock lock;

    public TenantManagementServiceImpl(
            LiquibaseProperties liquibaseProperties,
            ResourceLoader resourceLoader,
            TenantDataSourceService tenantDataSourceService,
            Lock lock
    ) {
        this.liquibaseProperties = liquibaseProperties;
        this.resourceLoader = resourceLoader;
        this.tenantDataSourceService = tenantDataSourceService;
//        this.tenantInitDataSourceService = tenantInitDataSourceService;
        this.lock = lock;
    }

    @Override
    public void createTenant(Tenant tenant) {

        tenantDataSourceService.verify(tenant);

        DataSource dataSource = null;
        final String lockName = "lock:tenant:manage" + tenant.getTenantId();
        try {
            lock.tryLock(lockName, 10000L);
//            tenantInitDataSourceService.initDataSource(tenant);
            dataSource = tenantDataSourceService.createDatasource(tenant);
        } catch (DataAccessException | InterruptedException e) {
            if (dataSource instanceof HikariDataSource hikariDataSource) {
                hikariDataSource.close();
            }
            throw new TenantCreationException("Error when creating db: " + tenant.getDb() + " or schema: " + tenant.getSchema(), e);
        } finally {
            lock.unlock(lockName);
        }


        if (dataSource != null) {
            try {
                lock.tryLock(lockName + "rent", 10000L);
                tenantDataSourceService.runLiquibase(tenant, dataSource, liquibaseProperties, resourceLoader);
            } catch (LiquibaseException | InterruptedException e) {
                if (dataSource instanceof HikariDataSource hikariDataSource) {
                    hikariDataSource.close();
                }
                throw new TenantCreationException("Error when populating db: ", e);
            } finally {
                lock.unlock(lockName + "rent");
            }
        }

        tenantDataSourceService.addTenantDataSource(tenant, dataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (lock == null) {

            log.warn("lock is null, use default lock");
            final ReentrantLock reentrantLock = new ReentrantLock();
            lock = new Lock() {
                @Override
                public boolean tryLock(String lockName) {
                    return reentrantLock.tryLock();
                }

                @Override
                public boolean tryLock(String lockName, long time, TimeUnit timeUnit) throws InterruptedException {
                    return reentrantLock.tryLock(time, timeUnit);
                }

                @Override
                public boolean tryLock(String lockName, long time) throws InterruptedException {
                    return tryLock("", time, TimeUnit.MILLISECONDS);
                }

                @Override
                public boolean tryLock(String lockName, long waitTime, long leaseTime) throws InterruptedException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void unlock(String lockName) {
                    reentrantLock.unlock();
                }
            };
        }
    }
}
