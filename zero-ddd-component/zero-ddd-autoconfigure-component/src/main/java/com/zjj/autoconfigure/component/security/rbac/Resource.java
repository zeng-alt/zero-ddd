package com.zjj.autoconfigure.component.security.rbac;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 16:26
 */
public interface Resource {

	public String getUri();

	public HttpMethod getMethod();

	default boolean compareTo(HttpServletRequest request) {
		return AntPathRequestMatcher.antMatcher(getMethod(), getUri()).matcher(request).isMatch();
	}

	default Mono<ServerWebExchangeMatcher.MatchResult> compareTo(ServerWebExchange request) {
		return new PathPatternParserServerWebExchangeMatcher(getUri(), getMethod()).matches(request);
	}

}
