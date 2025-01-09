package com.zjj.tenant.infrastructure.db.entity;

import com.zjj.domain.component.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月07日 21:21
 */
@Entity(name = "TenantMenu")
@Table(name = "tenant_menu")
@Getter
@Setter
public class TenantMenuEntity extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status = "0";

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;

    @ManyToOne
    @JoinColumn(name = "menu_resource_id")
    private MenuResourceEntity menuResource;
}
