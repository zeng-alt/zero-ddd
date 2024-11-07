package com.zjj.tenant.interfaces.mvc.transform;

import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import com.zjj.tenant.interfaces.mvc.form.TenantDataSourceForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 16:54
 */
@Mapper(componentModel = "spring")
public interface TenantDataSourceFormTransform {

    TenantDataSourceFormTransform INSTANCE = Mappers.getMapper(TenantDataSourceFormTransform.class);

    public StockInTenantDataSourceCmd transform(TenantDataSourceForm tenantDataSourceForm);
}
