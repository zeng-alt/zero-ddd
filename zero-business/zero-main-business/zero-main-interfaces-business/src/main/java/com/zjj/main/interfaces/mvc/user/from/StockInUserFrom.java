package com.zjj.main.interfaces.mvc.user.from;

import com.zjj.main.domain.user.StockInUserCmd;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月18日 21:45
 */
@Data
@AutoMapper(target = StockInUserCmd.class)
public class StockInUserFrom implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String avatar;
    private String password;
    private String status = "0";
    private Integer deleted = 0;
}
