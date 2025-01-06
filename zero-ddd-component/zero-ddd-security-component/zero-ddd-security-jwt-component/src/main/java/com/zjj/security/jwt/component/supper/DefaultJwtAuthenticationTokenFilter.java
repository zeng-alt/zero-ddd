package com.zjj.security.jwt.component.supper;

import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.jwt.component.JwtAuthenticationTokenFilter;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.security.jwt.component.JwtDetail;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:59
 */
public class DefaultJwtAuthenticationTokenFilter extends JwtAuthenticationTokenFilter {

	public DefaultJwtAuthenticationTokenFilter(JwtHelper jwtHelper, JwtCacheManage jwtCacheManage, JwtProperties jwtProperties) {
		super(jwtHelper, jwtCacheManage, jwtProperties);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(jwtHelper.tokenHeader());
		if (StringUtils.hasText(token)) {
			// 去掉Bearer前缀
			token = token.substring(7).trim();
			Map<String, Object> claims = jwtHelper.getClaimsFromToken(token);
			String soleId = (String) jwtHelper.getClaim(claims);

			UserDetails user = jwtCacheManage.get(soleId);
			if (user == null) {
				throw new BadCredentialsException("用户登录时间过期，重新登录");
			}

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
					null, user.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			LocalDateTime expire = LocalDateTime.now();
			if (user instanceof SecurityUser securityUser) {
				expire = securityUser.getExpire();
			}

			request.setAttribute(DefaultJwtRenewFilter.RENEW_KEY,
					JwtDetail.builder().id(soleId).user(user).expire(expire).build());

			CustomHeadRequestWrapper requestWrapper = new CustomHeadRequestWrapper(request);
			requestWrapper.addHeader(jwtProperties.getFastToken(), soleId);
			request = requestWrapper;
		}

		filterChain.doFilter(request, response);
	}

	public static class CustomHeadRequestWrapper extends HttpServletRequestWrapper {

		private Map<String, String> headerMap = new HashMap<>();

		public CustomHeadRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		public void addHeader(String name, String value) {
			headerMap.put(name, value);
		}

		@Override
		public String getHeader(String name) {
			String headerValue = super.getHeader(name);
			if (headerMap.containsKey(name)) {
				headerValue = headerMap.get(name);
			}
			return headerValue;
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> names = Collections.list(super.getHeaderNames());
            names.addAll(headerMap.keySet());
			return Collections.enumeration(names);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			List<String> values = Collections.list(super.getHeaders(name));
			if (headerMap.containsKey(name)) {
				values.add(headerMap.get(name));
			}
			return Collections.enumeration(values);
		}
	}

}
