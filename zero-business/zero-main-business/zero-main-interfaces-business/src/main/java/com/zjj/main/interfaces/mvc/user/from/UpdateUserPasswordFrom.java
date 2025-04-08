package com.zjj.main.interfaces.mvc.user.from;

import com.zjj.main.domain.user.cmd.StockInUserCmd;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 13:53
 */
@Data
@AutoMapper(target = StockInUserCmd.class)
public class UpdateUserPasswordFrom implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
