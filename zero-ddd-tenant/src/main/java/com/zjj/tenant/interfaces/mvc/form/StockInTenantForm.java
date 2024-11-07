package com.zjj.tenant.interfaces.mvc.form;

import java.time.LocalDateTime;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 14:26
 */
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
