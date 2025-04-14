package com.zjj.autoconfigure.component.security;

import com.zjj.autoconfigure.component.UtilException;
import com.zjj.autoconfigure.component.json.JsonHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 09:11
 */
public final class AuthenticationHelper {

	public static final String ERROR_KEY = "error:key";

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private AuthenticationHelper() {
	}

	public static void setErrorMsg(@NonNull HttpServletRequest request, @NonNull RuntimeException e) throws RuntimeException {
		request.setAttribute(ERROR_KEY, e.getMessage());
		throw e;
	}

	@NonNull
	public static Optional<String> getErrorMsg(@NonNull HttpServletRequest request) {
		Object attribute = request.getAttribute(ERROR_KEY);
		if (attribute == null) {
			return Optional.empty();
		}
		return Optional.of(attribute.toString());
	}

	public static void renderString(@NonNull HttpServletResponse response, int status, String msg, String data) {
		try {
			response.setStatus(status);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("utf-8");
//			String result = """
//					{
//					    "code":   "%d",
//					    "message":      "%s",
//					    "data":     "%s",
//					    "time":     "%s",
//					    "success": "%s"
//					}
//					""".formatted(status, msg, data, LocalDateTime.now().format(DATE_TIME_FORMATTER), status == 200);
			response.getWriter().print(data);
		}
		catch (IOException e) {
			throw new UtilException(e);
		}
	}

	public static void renderString(@NonNull HttpServletResponse response, int status, String msg) {
		try {
			response.setStatus(status);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("utf-8");
//			String result = """
//					{
//					    "code":   "%d",
//					    "message":      "%s",
//					    "time":     "%s",
//					    "success": "%s"
//					}
//					""".formatted(status, msg, LocalDateTime.now().format(DATE_TIME_FORMATTER), status == 200);
//			if (!HttpStatus.valueOf(status).is2xxSuccessful()) {
//				ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status), msg);
//				response.getWriter().print(JsonHelper.);
//			} else {
//				response.getWriter().print(msg);
//			}
			response.getWriter().print(msg);
		}
		catch (IOException e) {
			throw new UtilException(e);
		}
	}

	public static DataBuffer renderString(@NonNull ServerHttpResponse response, ServerHttpRequest request, int status, String msg) {
		String result = """
					{
					    "code":   "%d",
					    "message":      "%s",
					    "method": "%s",
					    "time":     "%s",
					    "url":	"%s",
					    "success": "%s"
					}
					""".formatted(status, msg,  request.getMethod(), LocalDateTime.now().format(DATE_TIME_FORMATTER), request.getURI(), status == 200);
		DataBufferFactory dataBufferFactory = response.bufferFactory();
		return dataBufferFactory.wrap(msg.getBytes(StandardCharsets.UTF_8));
	}

}
