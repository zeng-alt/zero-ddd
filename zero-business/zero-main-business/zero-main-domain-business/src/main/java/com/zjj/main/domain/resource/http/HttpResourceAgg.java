package com.zjj.main.domain.resource.http;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.resource.RbacResource;
import com.zjj.main.domain.resource.http.cmd.StockInHttpResourceCmd;
import com.zjj.main.domain.resource.http.event.StockInHttpResourceEvent;
import com.zjj.main.domain.role.event.DeleteRoleEvent;
import jakarta.validation.ValidationException;
import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:10
 */
@Data
public class HttpResourceAgg extends RbacResource<HttpResourceAgg, HttpResourceId> {

    private HttpResourceId id;
    private String path;
    private String redirect;
    private String method;
    private Boolean enable;
    private Long menuId;

    public void stock(StockInHttpResourceCmd cmd) {
        if (cmd.id() == null) return;

        if (!cmd.method().equals(this.method)) {
            throw new ValidationException("method不能修改");
        }

        if (!cmd.path().equals(this.path)) {
            throw new ValidationException("path不能修改");
        }

        if (!cmd.code().equals(super.getCode())) {
            throw new ValidationException("code不能修改");
        }

        StockInHttpResourceEvent event = BeanHelper.copyToObject(cmd, StockInHttpResourceEvent.class);
        ApplicationContextHelper.publisher().publishEvent(event);
    }

    public void delete() {
        ApplicationContextHelper.publisher().publishEvent(DeleteRoleEvent.of(super.getCode()));
    }
}
