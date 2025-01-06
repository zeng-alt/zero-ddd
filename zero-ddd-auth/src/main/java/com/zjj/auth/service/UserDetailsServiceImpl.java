package com.zjj.auth.service;

import com.zjj.auth.repository.UserRepository;
import com.zjj.autoconfigure.component.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月06日 09:06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> {
                    List<String> roles = user.getUserRoles().stream().map(role -> role.getRole().getRoleKey()).toList();
                    return SecurityUser
                            .withUsername(user.getUsername())
                            .password(user.getPassword())
                            .tenant(user.getTenantBy())
                            .roles(roles)
                            .currentRole(CollectionUtils.isEmpty(roles) ? null : roles.iterator().next())
                            .disabled(!"0".equals(user.getStatus()))
                            .build();
                }).getOrElseThrow(() -> new UsernameNotFoundException(username + " 用户找不到"));
    }
}
