package com.zjj.main.domain.role;

import com.zjj.main.domain.role.cmd.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 11:26
 */
public interface Role {
    void functionAuthorize(FunctionAuthorizeCmd cmd);

    void functionCancelAuthorize(FunctionCancelAuthorizeCmd cmd);

    void serviceAuthorize(ServiceAuthorizeCmd cmd);

    void serviceCancelAuthorize(ServiceCancelAuthorizeCmd cmd);

    void authorizePermission(AuthorizePermissionCmd cmd);

    void delete();
}
