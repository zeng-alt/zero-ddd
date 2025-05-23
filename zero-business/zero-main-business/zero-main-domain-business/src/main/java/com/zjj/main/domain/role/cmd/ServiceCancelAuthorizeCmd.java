package com.zjj.main.domain.role.cmd;

import lombok.Data;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月23日 09:55
 */
@Data
public class ServiceCancelAuthorizeCmd {
    private String service;
    private Set<Long> roleIds;
}
