package com.zjj.security.core.component.abac;

import lombok.Data;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月03日 16:23
 */
@Data
public class RolePermission {
    private String role;
    private Set<String> permissions;
}
