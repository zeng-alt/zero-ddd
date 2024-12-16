package com.zjj.security.abac.component.spi;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:13
 */
public interface SubjectAttribute {

    Object getSubject(Authentication authentication);
}
