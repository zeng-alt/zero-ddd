package com.zjj.main.domain.user;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.domain.component.Aggregate;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:24
 */
@Data
public class UserAgg extends Aggregate<Long> {
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
    private String tenantBy;
    private Set<String> roleIds;

    public UserAgg assignRoles(List<String> roleIds) {
        this.roleIds.addAll(roleIds);
        ApplicationContextHelper.publisher().publishEvent(null);
        return this;
    }
}
