package com.zjj.main.interfaces.mvc.role.from;

import lombok.Data;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月22日 21:57
 */
@Data
public class ServicePermissionFrom {
    private String service;
    private Set<Long> roleIds;
}
