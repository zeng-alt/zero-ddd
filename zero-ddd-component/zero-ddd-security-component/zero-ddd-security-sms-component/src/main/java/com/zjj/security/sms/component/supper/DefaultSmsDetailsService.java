package com.zjj.security.sms.component.supper;

import com.zjj.security.sms.component.SmsDetailsService;
import com.zjj.security.sms.component.configuration.DefaultSmsLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月07日 19:53
 * @version 1.0
 */
public class DefaultSmsDetailsService implements SmsDetailsService {

    private final Map<String, UserDetails> map;

    public DefaultSmsDetailsService() {
        this.map = Map.of(
                "123456789", User.builder().username("root").password("123456").build()
        );
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        return map.get(mobile);
    }
}
