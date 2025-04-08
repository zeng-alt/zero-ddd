package com.zjj.autoconfigure.component.security.rbac.router;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 16:44
 */
@Data
public class RouteTemplateEvent {

    public String contextPath;
    public List<String> templates;
}
