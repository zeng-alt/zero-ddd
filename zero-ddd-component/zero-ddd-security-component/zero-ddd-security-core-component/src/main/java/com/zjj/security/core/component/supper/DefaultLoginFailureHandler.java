package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.json.JsonUtils;
import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.net.URI;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:36
 */
public class DefaultLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		String message = AuthenticationHelper.getErrorMsg(request).orElse(exception.getMessage());
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, message);
		problemDetail.setInstance(URI.create(request.getRequestURI()));
		problemDetail.setTitle("登录失败");

		AuthenticationHelper.renderString(
				response,
				HttpStatus.UNAUTHORIZED.value(),
				message,
				JsonUtils.toJsonString(problemDetail)
		);
	}

}
