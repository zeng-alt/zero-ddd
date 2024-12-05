package com.zjj.tenant.component.supper;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.zjj.tenant.component.spi.DynamicSourceManage;


import javax.sql.DataSource;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:25
 */

public class DefaultDynamicSourceManage implements DynamicSourceManage {

    private final DynamicRoutingDataSource dynamicRoutingDataSource;
    private final String db;

    public DefaultDynamicSourceManage(DynamicRoutingDataSource dynamicRoutingDataSource, DynamicDataSourceProperties db) {
        this.dynamicRoutingDataSource = dynamicRoutingDataSource;
        this.db = db.getPrimary();
    }

    public String getPrimary() {
        return db;
    }

    public void addDataSource(String db, DataSource dataSource) {
        dynamicRoutingDataSource.addDataSource(db, dataSource);
    }

    public void removeDataSource(String db) {
        dynamicRoutingDataSource.removeDataSource(db);
    }

    public void switchDataSource(String db) {
        DynamicDataSourceContextHolder.clear();
        //切换到对应poolName的数据源
        DynamicDataSourceContextHolder.push(db);
    }


    public void switchPrimaryDataSource() {
        switchDataSource(db);
    }


    public Set<String> getAllDs() {
        return dynamicRoutingDataSource.getDataSources().keySet();
    }
}
