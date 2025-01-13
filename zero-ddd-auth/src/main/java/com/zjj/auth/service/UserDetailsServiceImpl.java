package com.zjj.auth.service;

import com.zjj.auth.repository.UserRepository;
import com.zjj.autoconfigure.component.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2025年01月05日 14:39
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(user -> {
                    List<String> roles = user.getUserRoles().stream().map(userRole -> userRole.getRole().getRoleKey()).toList();
                    return SecurityUser
                            .withUsername(user.getUsername())
                            .password(user.getPassword())
                            .tenant(user.getTenantBy())
                            .disabled(!"0".equals(user.getStatus()))
                            .roles(roles)
                            .currentRole(CollectionUtils.isEmpty(roles) ? null : roles.iterator().next())
                            .build();
                }).getOrElseThrow(() -> new UsernameNotFoundException(username + " 用户名不存在"));
    }
}
