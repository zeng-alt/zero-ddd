package com.zjj.autoconfigure.component.security.abac;

import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
 * 数据的存储最好是登录后，根据登录的用户名拿到主体的信息并保存在缓存中，这样在每次请求的时候，直接从缓存中拿取，在微服务架构中可以通用化。<br/>
 * 可以使用spring security提供的登录成功事件来完成{@link AuthenticationSuccessEvent}
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:13
 */
public interface SubjectAttribute<T> {

    T getSubject(String username, String tenant);
}
