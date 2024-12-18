package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.LoginSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:05
 */
public class DefaultLoginSuccessHandler extends LoginSuccessHandler {

	public DefaultLoginSuccessHandler(JwtCacheManage jwtCacheManage, JwtHelper jwtHelper) {
		super(jwtCacheManage, jwtHelper);
	}

	/**
	 * @param request request
	 * @param response response
	 * @param authentication 登录成功的用户
	 * @throws IOException io
	 * @throws ServletException servlet
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String soleId = principal.getUsername() + ":" + UUID.randomUUID();
		String jwt = jwtHelper.generateJWT(soleId);
		jwtCacheManage.put(soleId, principal);
		AuthenticationHelper.renderString(response, HttpStatus.OK.value(), "登录成功", jwt);
	}

}
