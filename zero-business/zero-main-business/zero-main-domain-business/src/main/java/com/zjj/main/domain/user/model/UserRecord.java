package com.zjj.main.domain.user.model;

import com.zjj.autoconfigure.component.security.UserProfile;
import lombok.Data;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月02日 15:38
 */
@Data
public class UserRecord {
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
    private String status;
    private Integer deleted;
    private String tenantBy;
    private UserProfile profile;
    private Set<RoleRecord> roles;
}
