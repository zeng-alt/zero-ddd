package com.zjj.security.rabac.component.supper;

import com.zjj.security.rabac.component.domain.HttpResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 22:36
 */
public class DefaultRbacAccessService implements RbacAccessService {

	private final HttpResourceService httpResourceService;

	public DefaultRbacAccessService(@NonNull HttpResourceService httpResourceService) {
		this.httpResourceService = httpResourceService;
	}

	@NonNull
	@Override
	public boolean verify(@NonNull Object principal, @NonNull Collection<? extends GrantedAuthority> grantedAuthorities,
			@NonNull HttpServletRequest request) {
		Set<HttpResource> httpResources = httpResourceService.queryByPrincipal(principal);
		Set<HttpResource> httpResources1 = httpResourceService.queryByGrantedAuthority(grantedAuthorities);

		return httpResources.stream().anyMatch(httpResource -> httpResource.compareTo(request))
				|| httpResources1.stream().anyMatch(httpResource -> httpResource.compareTo(request));

	}

}
