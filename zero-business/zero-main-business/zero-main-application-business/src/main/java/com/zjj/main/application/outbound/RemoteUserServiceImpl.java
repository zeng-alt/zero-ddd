package com.zjj.main.application.outbound;

import com.zjj.autoconfigure.component.security.RoleGrantedAuthority;
import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.exchange.main.api.RemoteUserApi;
import com.zjj.main.domain.user.UserQueryModel;
import com.zjj.main.domain.user.model.RoleRecord;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.layered.ApplicationLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 14:58
 */
@Service
@Slf4j
@ApplicationLayer
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RemoteUserServiceImpl implements RemoteUserApi {


    private final UserQueryModel userQueryModel;

    @Override
    public Option<SecurityUser> findByUsername(String username) {

        return userQueryModel
                .findByUsername(username)
                .map(user -> {
                    Set<RoleRecord> roles = user.getRoles();
                    List<GrantedAuthority> authorities = getGrantedAuthorities(roles);
                    return SecurityUser
                            .withUsername(user.getUsername())
                            .roles(authorities)
                            .currentRole(CollectionUtils.isEmpty(authorities) ? null : authorities.iterator().next())
                            .password(user.getPassword())
                            .tenant(user.getTenantBy())
                            .disabled(!"0".equals(user.getStatus()))
                            .build();
                })
                .peek(securityUser -> log.info("findByUsername: {}", securityUser));
    }

    private static List<GrantedAuthority> getGrantedAuthorities(Set<RoleRecord> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (RoleRecord role : roles) {
                RoleGrantedAuthority authority = new RoleGrantedAuthority();
                authority.setCode(role.getCode());
                authority.setName(role.getName());
                authority.setEnable(role.getEnable());
                authorities.add(authority);
            }
        }
        return authorities;
    }
}
