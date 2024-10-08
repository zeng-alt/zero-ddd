package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 20:06
 */
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		AuthenticationHelper.renderString(response, HttpStatus.UNAUTHORIZED.value(),
				"验证失败: " + AuthenticationHelper.getErrorMsg(request).orElse(exception.getMessage()));
	}

}
