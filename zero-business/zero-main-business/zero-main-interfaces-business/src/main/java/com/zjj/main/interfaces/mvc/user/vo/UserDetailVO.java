package com.zjj.main.interfaces.mvc.user.vo;

import com.zjj.autoconfigure.component.security.UserProfile;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月08日 10:40
 */
@Data
public class UserDetailVO {


    private UserProfile profile;
    private String username;
    private String tenant;
    private String database;
    private String schema;

    //存储权限信息
    private Set<GrantedAuthority> roles;

    private GrantedAuthority currentRole;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;
    private LocalDateTime expire;
}
