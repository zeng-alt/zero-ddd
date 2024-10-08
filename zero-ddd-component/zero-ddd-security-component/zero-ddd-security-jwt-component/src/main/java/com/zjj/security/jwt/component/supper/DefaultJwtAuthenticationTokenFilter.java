package com.zjj.security.jwt.component.supper;

import com.zjj.security.jwt.component.JwtAuthenticationTokenFilter;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.security.jwt.component.JwtDetail;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:59
 */
public class DefaultJwtAuthenticationTokenFilter extends JwtAuthenticationTokenFilter {

	public DefaultJwtAuthenticationTokenFilter(JwtHelper jwtHelper, JwtCacheManage jwtCacheManage) {
		super(jwtHelper, jwtCacheManage);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(jwtHelper.tokenHeader());
		if (StringUtils.hasText(token)) {
			Map<String, Object> claims = jwtHelper.getClaimsFromToken(token);
			String username = (String) jwtHelper.getClaim(claims);
			LocalDateTime expire = jwtHelper.getExpire(claims);
			UserDetails user = jwtCacheManage.get(username);
			if (user == null) {
				throw new BadCredentialsException("用户登录时间过期，重新登录");
			}

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
					null, user.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			request.setAttribute(DefaultJwtRenewFilter.RENEW_KEY,
					JwtDetail.builder().user(user).expire(expire).build());
		}

		filterChain.doFilter(request, response);
	}

}
