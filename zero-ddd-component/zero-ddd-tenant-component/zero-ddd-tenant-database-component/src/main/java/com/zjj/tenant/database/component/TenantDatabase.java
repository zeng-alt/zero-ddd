package com.zjj.tenant.database.component;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月04日 11:24
 */
@Data
public class TenantDatabase {
    private String tenantId;

    private String db;

    private String password;

    private String url;
}
