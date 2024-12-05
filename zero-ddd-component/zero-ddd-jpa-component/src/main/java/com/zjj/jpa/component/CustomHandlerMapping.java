package com.zjj.jpa.component;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 08:55
 */
@Component
public class CustomHandlerMapping extends AbstractHandlerMapping {

	private final Map<String, HttpRequestHandler> mapping;

	@Autowired
	public CustomHandlerMapping(List<JpaBaseEntity> jpaBaseEntitys) {
		mapping = new HashMap<>();
	}

	@Override
	protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
		String lookupPathForRequest = getUrlPathHelper().getLookupPathForRequest(request);
		return mapping.get(lookupPathForRequest);
	}

}
