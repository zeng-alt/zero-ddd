package com.zjj.security.rabac.component.supper;

import com.zjj.security.rabac.component.domain.HttpResource;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 22:51
 */
public class DefaultHttpResourceService implements HttpResourceService {

	@Override
	public Set<HttpResource> queryByPrincipal(Object principal) {
		return null;
	}

	@Override
	public Set<HttpResource> queryByGrantedAuthority(Collection<? extends GrantedAuthority> grantedAuthorities) {
		return null;
	}

}
