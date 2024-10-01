package com.zjj.security.sms.component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 16:55
 */
public interface CodeService {

    String getCode(CharSequence mobile);

    boolean matches(CharSequence mobile, String code);
}
