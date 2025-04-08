package com.zjj.auth.service;

import com.zjj.auth.entity.UserRole;
import com.zjj.auth.repository.UserRepository;
import com.zjj.autoconfigure.component.security.RoleGrantedAuthority;
import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @crateTime 2025年01月05日 14:39
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final TenantSingleDataSourceProvider tenantSingleDataSourceProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        tenantSingleDataSourceProvider
                .findById(TenantContextHolder.getTenantId())
                .forEach(t -> {
                    TenantContextHolder.setDatabase(t.getDb());
                    TenantContextHolder.setSchema(t.getSchema());
                });

        return userRepository
                .findByUsername(username)
                .map(user -> {
                    Set<GrantedAuthority> roles = user.getUserRoles().stream().map(UserRole::getRole).map(r -> {
                        RoleGrantedAuthority authority = new RoleGrantedAuthority();
                        authority.setEnable(r.getEnable());
                        authority.setCode(r.getCode());
                        authority.setName(r.getName());
                        return authority;
                    }).collect(Collectors.toSet());
                    Tenant tenant = tenantSingleDataSourceProvider.findById(user.getTenantBy()).getOrElse(new Tenant());
                    return SecurityUser
                            .withUsername(user.getUsername())
                            .password(user.getPassword())
                            .tenant(user.getTenantBy())
                            .database(tenant.getDb())
                            .schema(tenant.getSchema())
                            .disabled(!"0".equals(user.getStatus()))
                            .roles(roles)
                            .currentRole(CollectionUtils.isEmpty(roles) ? null : roles.iterator().next())
                            .build();
                }).getOrElseThrow(() -> new UsernameNotFoundException(username + " 用户名不存在"));
    }
}
