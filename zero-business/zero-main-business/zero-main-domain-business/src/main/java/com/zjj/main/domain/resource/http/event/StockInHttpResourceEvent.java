package com.zjj.main.domain.resource.http.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:17
 */
@Data
@DomainEvent
public class StockInHttpResourceEvent extends TenantEvent {
    private Long id;
    private String resourceType;
    private String code;
    private String name;
    private String path;
    private String redirect;
    private String method;
    private Boolean enable;
    private Long menuId;
}
