package com.zjj.main.infrastructure.db.jpa.project;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月09日 14:54
 */
@Data
public class PermissionProject {

    private Long id;

    private String resourceType;

    private String code;

    private String name;

    private String tenantBy;

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
