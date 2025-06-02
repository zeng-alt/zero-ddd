package com.zjj.main.domain.role.cmd;

import lombok.Data;
import org.jmolecules.architecture.cqrs.Command;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月24日 22:35
 */
@Data
@Command
public class AuthorizePermissionCmd {
    private Set<Long> roleIds;
    private Set<Long> permissionIds;
}
