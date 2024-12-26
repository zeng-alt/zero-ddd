package com.zjj.auth.config;

import com.zjj.exchange.main.client.RemoteUserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 21:38
 */
@Configuration
public class AuthConfig {

    @Bean
    public UserDetailsService userDetailsService(RemoteUserClient remoteUserClient) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return remoteUserClient.findByUsername(username)
                        .map(user -> {
                            user.setAccountNonLocked(true);
                            return user;
                        })
                        .getOrElseThrow(() -> new UsernameNotFoundException(username + "用户不存在"));
            }
        };
    }
}
