package com.zjj.security.sms.component;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 16:55
 */
public interface CodeService {

	@Nullable
	String getCode(CharSequence mobile);

	@NonNull
	boolean matches(CharSequence mobile, String code);

}
