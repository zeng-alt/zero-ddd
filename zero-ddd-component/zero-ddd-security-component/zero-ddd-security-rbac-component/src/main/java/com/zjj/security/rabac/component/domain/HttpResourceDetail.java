package com.zjj.security.rabac.component.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 22:50
 */
@Data
@Builder
public class HttpResourceDetail implements HttpResource {

	private String uri;

	private String method;

}
