package com.zjj.tenant.component.entity;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月24日 20:06
 */
public interface ITenantEntity {


    public String getTenantKey();

    public String getPoolName();


//    default Class<? extends DataSource> getType() throws ClassNotFoundException {
//        return (Class<? extends DataSource>) Class.forName(getDriverClassName());
//    }

    public String getDriverClassName();

    public String getUrl();

    public String getUsername();

    public String getPassword();

    public String getJndiName();

    public Boolean getSeata();

    public Boolean getP6spy();

    public Boolean getLazy();

    public String getPublicKey();


    public boolean getEnabled();
}
