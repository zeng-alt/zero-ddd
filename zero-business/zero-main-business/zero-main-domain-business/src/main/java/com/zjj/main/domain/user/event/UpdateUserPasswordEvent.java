package com.zjj.main.domain.user.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 13:36
 */
@Data
@DomainEvent
public class UpdateUserPasswordEvent extends TenantEvent {

    private Long userId;
    private String password;

    public static UpdateUserPasswordEvent of(Long userId, String password) {
        UpdateUserPasswordEvent event = new UpdateUserPasswordEvent();
        event.setUserId(userId);
        event.setPassword(password);
        return event;
    }
}
