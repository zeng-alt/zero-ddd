package com.zjj.auth;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月12日 14:45
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private Integer age;
    private BigDecimal bigDecimal;

    public static User createUser() {
        User user = new User();
        user.setUsername("张三");
        user.setAge(14);
        user.setBigDecimal(new BigDecimal("0.1"));
        return user;
    }
}
