package com.zjj.main.domain.user;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.domain.component.Aggregate;
import com.zjj.main.domain.user.event.UpdateUserPasswordEvent;
import io.micrometer.common.util.StringUtils;
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

    public void updatePassword(String password) {
        // 对密码进行校验
        if (StringUtils.isEmpty(password)) {

        }
        if (password.length() < 6) {

        }
        // 密码必须包含数据和字母
        if (!password.matches(".*[a-zA-Z]+.*") || !password.matches(".*[0-9]+.*")) {

        }

        ApplicationContextHelper.publisher().publishEvent(UpdateUserPasswordEvent.of(this.id, password));
    }
}
