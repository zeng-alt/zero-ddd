package com.zjj.security.sms.component;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 20:39
 */
public interface SmsDetailsService {

    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;
}
