package com.zjj.tenant.interfaces.mvc.form;

import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:26
 */
@Data
@AutoMapper(target = StockInTenantDataSourceCmd.class, reverseConvertGenerate = false)
public class TenantDataSourceForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long tenantId;
    private String db;
    private String password;
    private String schema;
    private Boolean enabled;
}
