package com.zjj.autoconfigure.component.security;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月02日 21:24
 */
@Data
public class UserProfile {
    private String nickName;
    private String gender;
    private String avatar;
    private String address;
    private String email;
}
