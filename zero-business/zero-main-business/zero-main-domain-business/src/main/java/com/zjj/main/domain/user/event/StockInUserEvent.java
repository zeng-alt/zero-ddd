package com.zjj.main.domain.user.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 17:13
 */
@Data
@DomainEvent
public class StockInUserEvent extends TenantEvent {
    private Long id;
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String avatar;
    private String status;
    private Integer deleted;
}
