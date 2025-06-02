package com.zjj.main.interfaces.mvc.role.from;

import lombok.Data;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月24日 22:34
 */
@Data
public class AuthorizePermissionFrom {
    private Set<Long> roleIds;
    private Set<Long> permissionIds;
}
