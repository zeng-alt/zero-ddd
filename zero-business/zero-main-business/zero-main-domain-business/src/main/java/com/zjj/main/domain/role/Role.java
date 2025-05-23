package com.zjj.main.domain.role;

import com.zjj.main.domain.role.cmd.FunctionAuthorizeCmd;
import com.zjj.main.domain.role.cmd.FunctionCancelAuthorizeCmd;
import com.zjj.main.domain.role.cmd.ServiceAuthorizeCmd;
import com.zjj.main.domain.role.cmd.ServiceCancelAuthorizeCmd;

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
}
