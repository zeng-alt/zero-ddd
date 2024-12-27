package com.zjj.security.jwt.component.supper;

import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.jwt.component.JwtDetail;
import com.zjj.security.jwt.component.JwtRenewFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 16:43
 */
public class DefaultJwtRenewFilter extends JwtRenewFilter {

	public static final String RENEW_KEY = "renew:jwt:key";
	private final Duration expireTime;

	public DefaultJwtRenewFilter(JwtCacheManage jwtCacheManage, JwtProperties jwtProperties) {
		super(jwtCacheManage);
		this.expireTime = Duration.of(jwtProperties.getExpiration(), jwtProperties.getTemporalUnit());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Object attribute = request.getAttribute(RENEW_KEY);
		if (Objects.nonNull(attribute)) {
			JwtDetail jwtDetail = (JwtDetail) attribute;
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expire = jwtDetail.getExpire();
			// 如果过期时间小当前时间的前15分钟，不进行刷新
			if (expire.isBefore(now.plusMinutes(15))) {
				UserDetails user = jwtDetail.getUser();
				if (user instanceof SecurityUser securityUser) {
					securityUser.setExpire(now.plus(expireTime));
				}
				jwtCacheManage.put(jwtDetail.getId(), user);
			}
		}

		filterChain.doFilter(request, response);
	}

}
