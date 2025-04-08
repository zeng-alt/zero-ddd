package com.zjj.main.domain.user.model;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月02日 15:39
 */
@Data
public class RoleRecord {
    private Long id;
    private String code;
    private String name;
    private Boolean enable;
}
