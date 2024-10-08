package com.zjj.tenant.component.entity;

import com.zjj.core.component.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月27日 20:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenantEntity extends BaseEntity {

	/**
	 * 租户编号
	 */
	private String tenantId;

}
