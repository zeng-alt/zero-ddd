package com.zjj.security.sms.component.supper;

//import com.github.benmanes.caffeine.cache.Cache;
import com.zjj.security.sms.component.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;

import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 21:11
 */
@RequiredArgsConstructor
public class DefaultCodeService implements CodeService {

	private final Cache cache;

	@Override
	public String getCode(CharSequence mobile) {
		String value = cache.get(mobile, String.class);
		cache.evict(mobile);
		return value;
	}

	@Override
	public boolean matches(CharSequence mobile, String code) {
		String value = cache.get(mobile, String.class);
		if (Objects.isNull(value)) {
			return false;
		}
		cache.evict(mobile);
		return value.equals(code);
	}

}
