package com.zjj.tenant.interfaces.mvc.form;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:26
 */
@Data
public class TenantDataSourceForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long tenantId;
    private String poolName;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String jndiName;
    private Boolean seata;
    private Boolean p6spy;
    private Boolean lazy;
    private String publicKey;
    private Boolean enable;
}
