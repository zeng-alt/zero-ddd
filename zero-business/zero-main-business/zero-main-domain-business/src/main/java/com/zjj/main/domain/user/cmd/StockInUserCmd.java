package com.zjj.main.domain.user.cmd;

import org.jmolecules.architecture.cqrs.Command;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:27
 */
@Command
public record StockInUserCmd(
        Long id,
        String username,
        String password,
        String nickName,
        String email,
        String phoneNumber,
        String gender,
        String avatar,
        String status,
        Integer deleted
) {
}
