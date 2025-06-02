//package com.zjj.main.infrastructure.db.jpa.entity;
//
//import com.zjj.domain.component.BaseEntity;
//import com.zjj.domain.component.TenantAuditable;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.TenantId;
//import org.springframework.lang.Nullable;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2025年05月25日 16:57
// */
//@Getter
//@Setter
//@Entity
//@Table(name = "main_role_menu")
//public class RoleMenu extends BaseEntity<Long> implements TenantAuditable<String> {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    @ManyToOne
//    @JoinColumn(name = "menu_id")
//    private MenuResource menuResource;
//
//    @TenantId
//    @Nullable
//    private String tenantBy;
//}
