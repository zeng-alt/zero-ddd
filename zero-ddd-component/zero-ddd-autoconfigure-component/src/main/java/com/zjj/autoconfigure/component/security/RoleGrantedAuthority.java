package com.zjj.autoconfigure.component.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月02日 21:22
 */
@Data
public class RoleGrantedAuthority implements GrantedAuthority {

    private String code;
    private String name;
    private Boolean enable;

    @Override
    public String getAuthority() {
        return code;
    }
}
