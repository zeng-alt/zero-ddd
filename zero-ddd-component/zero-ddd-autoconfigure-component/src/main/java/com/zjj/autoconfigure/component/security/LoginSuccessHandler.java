package com.zjj.autoconfigure.component.security;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:01
 */
public abstract class LoginSuccessHandler implements AuthenticationSuccessHandler {

	protected final JwtCacheManage jwtCacheManage;

	protected final JwtHelper jwtHelper;
	protected final JwtProperties jwtProperties;

	protected LoginSuccessHandler(JwtCacheManage jwtCacheManage, JwtHelper jwtHelper, JwtProperties jwtProperties) {
		this.jwtCacheManage = jwtCacheManage;
		this.jwtHelper = jwtHelper;
		this.jwtProperties = jwtProperties;
	}

}
