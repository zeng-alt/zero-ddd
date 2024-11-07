package com.zjj.tenant.interfaces.mvc.transform;

import com.zjj.tenant.domain.tenant.cmd.StockInTenantCmd;
import com.zjj.tenant.interfaces.mvc.form.StockInTenantForm;
import com.zjj.tenant.interfaces.mvc.form.TenantDataSourceForm;
import org.mapstruct.Mapper;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 20:30
 */
@Mapper(componentModel = "spring")
public interface StockInTenantFormTransform {

    public StockInTenantCmd transform(StockInTenantForm stockInTenantForm);
}
