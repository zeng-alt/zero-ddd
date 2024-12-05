package com.zjj.tenant.component.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月27日 20:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class TenantBaseEntity extends BaseEntity {

	/**
	 * 租户编号
	 */
	private String tenantKey;

	private String tenantName;

}
