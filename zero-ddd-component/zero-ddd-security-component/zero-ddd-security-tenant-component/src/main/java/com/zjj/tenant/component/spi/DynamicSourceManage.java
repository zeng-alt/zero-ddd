package com.zjj.tenant.component.spi;

import javax.sql.DataSource;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:25
 */
public interface DynamicSourceManage {

    public void addDataSource(String db, DataSource dataSource);


    public Set<String> getAllDs();
    public String getPrimary();

    public void switchDataSource(String db);

    public void switchPrimaryDataSource();
}
