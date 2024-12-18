package com.zjj.autoconfigure.component.security;

import com.zjj.autoconfigure.component.UtilException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
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
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("utf-8");
			String result = """
					{
					    "code":   "%d",
					    "message":      "%s",
					    "data":     "%s",
					    "time":     "%s",
					    "success": "%s"
					}
					""".formatted(status, msg, data, LocalDateTime.now().format(DATE_TIME_FORMATTER), status == 200);
			response.getWriter().print(result);
		}
		catch (IOException e) {
			throw new UtilException(e);
		}
	}

	public static void renderString(@NonNull HttpServletResponse response, int status, String msg) {
		try {
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("utf-8");
			String result = """
					{
					    "code":   "%d",
					    "message":      "%s",
					    "time":     "%s",
					    "success": "%s"
					}
					""".formatted(status, msg, LocalDateTime.now().format(DATE_TIME_FORMATTER), status == 200);
			response.getWriter().print(result);
		}
		catch (IOException e) {
			throw new UtilException(e);
		}
	}

	public static DataBuffer renderString(@NonNull ServerHttpResponse response, int status, String msg) {
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		String result = """
					{
					    "code":   "%d",
					    "message":      "%s",
					    "time":     "%s",
					    "success": "%s"
					}
					""".formatted(status, msg, LocalDateTime.now().format(DATE_TIME_FORMATTER), status == 200);
		DataBufferFactory dataBufferFactory = response.bufferFactory();
		return dataBufferFactory.wrap(result.getBytes(Charset.defaultCharset()));
	}

}
