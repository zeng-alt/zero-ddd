package com.zjj.security.jwt.component;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:52
 */
@Data
@Builder
public class JwtDetail implements Serializable {
    private UserDetails user;
    private LocalDateTime expire;
    private String id;
}
