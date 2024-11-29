package com.zjj.security.rabac.component.supper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 15:28
 */
public interface RbacAccessService {


	@NonNull
	public boolean verify(@NonNull Object principal, @NonNull Collection<? extends GrantedAuthority> grantedAuthorities,
			@NonNull HttpServletRequest request);

}
