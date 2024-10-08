package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 20:15
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
			throws IOException, ServletException {
		AuthenticationHelper.renderString(response, HttpStatus.FORBIDDEN.value(),
				"访问拒绝: " + AuthenticationHelper.getErrorMsg(request).orElse(exception.getMessage()));
	}

}
