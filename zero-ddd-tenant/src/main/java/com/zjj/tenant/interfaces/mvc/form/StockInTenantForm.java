package com.zjj.tenant.interfaces.mvc.form;

import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantCmd;
import io.github.linpeilie.annotations.AutoMapper;

import java.time.LocalDateTime;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:26
 */
@AutoMapper(target = StockInTenantCmd.class, reverseConvertGenerate = false)
public record StockInTenantForm(
        String tenantKey,
        String contactUsername,
        String contactPhone,
        String companyName,
        String licenseNumber,
        String address,
        String domain,
        String intro,
        String remark,
        LocalDateTime expi,
        Long accountCount
) {
}
