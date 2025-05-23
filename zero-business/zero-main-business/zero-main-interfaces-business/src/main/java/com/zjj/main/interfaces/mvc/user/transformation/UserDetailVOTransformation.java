package com.zjj.main.interfaces.mvc.user.transformation;

import com.zjj.autoconfigure.component.security.RoleGrantedAuthority;
import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.main.domain.user.UserAgg;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import com.zjj.main.infrastructure.db.jpa.entity.User;
import com.zjj.main.infrastructure.db.jpa.entity.UserRole;
import com.zjj.main.interfaces.mvc.user.vo.UserDetailVO;
import org.checkerframework.checker.optional.qual.MaybePresent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月08日 11:10
 */
@Mapper(componentModel = "spring")
public interface UserDetailVOTransformation {

    @Mapping(target = "profile", source = "profile")
    public UserDetailVO to(SecurityUser user, UserProfile profile);

//    @Mapping(target = "enabled", source = "java(!\"0\".equals(userAgg.getStatus()))")
    @Mapping(target = "profile", source = "userAgg")
    public UserDetailVO to(UserAgg userAgg);

    @Mapping(target = "roles", source = "user", qualifiedByName = "mapGrantedAuthority")
    @Mapping(target = "profile", source = "user")
    @Mapping(target = "tenant", source = "tenantBy")
//    @Mapping(target = "enabled", expression = "java(!\"0\".equals(user.getStatus()))")
    public UserDetailVO to(User user);

    @Named("mapGrantedAuthority")
    default Set<GrantedAuthority> map(User user) {
        return user.getUserRoles().stream().map(UserRole::getRole).map(r -> {
            RoleGrantedAuthority grantedAuthority = new RoleGrantedAuthority();
            grantedAuthority.setCode(r.getCode());
            grantedAuthority.setName(r.getName());
            grantedAuthority.setEnable(r.getEnable());
            return grantedAuthority;
        }).collect(Collectors.toSet());
    }
}
