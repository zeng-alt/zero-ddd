package com.zjj.autoconfigure.component.security.abac;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:13
 */
public interface SubjectAttribute {

    Object getSubject(String username, String tenant);
}
